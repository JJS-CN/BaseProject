package com.module.base

import android.os.Build
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.MetaDataUtils
import com.blankj.utilcode.util.ToastUtils
import com.common.core.network.base.BaseLoggingInterceptor
import com.common.core.network.base.CallFactoryProxy
import com.common.core.network.datasource.RemoteExtendDataSource
import com.common.core.network.viewmodel.IUIActionEvent
import com.common.core.manager.UUIDManager
import com.common.core.network.exception.ServerCodeBadException
import com.google.gson.Gson
import com.module.base.manager.YtUserManager
import com.module.base.network.SERVICE_TYPE
import com.module.base.network.SignUtil
import com.module.base.network.TokenService
import com.module.base.router.ARouteAction
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit


/**
 *  Class: 用于统一设置拦截器或其他项目有关的配置修改。
 *  Other:
 *  Create by jsji on  2021/7/1.
 */
open class BaseDataSource<Api : Any>(
  iActionEvent: IUIActionEvent?,
  apiServiceClass: Class<Api>
) :
  RemoteExtendDataSource<Api>(iActionEvent, apiServiceClass) {
  // TODO: 2021/7/5  需要暴露单独实例化获取apiservice的方法或能力，以便在不同module中实例化接口文件进行管理
  companion object {
    public val httpClient: OkHttpClient by lazy {
      createHttpClient()
    }

    private fun createHttpClient(): OkHttpClient {
      var logInterceptor = BaseLoggingInterceptor()
      logInterceptor.level =
        if(BuildConfig.DEBUG)
          BaseLoggingInterceptor.Level.BODY
        else
          BaseLoggingInterceptor.Level.NONE

      val builder = OkHttpClient.Builder()
        .readTimeout(10000L, TimeUnit.MILLISECONDS)
        .writeTimeout(10000L, TimeUnit.MILLISECONDS)
        .connectTimeout(10000L, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(true)

        .addInterceptor(Interceptor { chain ->
          //常用的参数，都会进行添加
          val originalRequest = chain.request()
          //构建新的请求体
          lateinit var builder: Request.Builder
          // 添加公共参数
          val map = HashMap<String, Any>()
          //map["token"] = "token"
          if("POST" == originalRequest.method) {
            //重新构建requestBody，应用json格式
            if(originalRequest.body is FormBody) {
              val formBody = originalRequest.body as FormBody
              // 先复制原来的参数
              for(i in 0 until formBody.size) {
                map[formBody.encodedName(i)] = formBody.encodedValue(i)
              }
              val body: RequestBody =
                Gson().toJson(map).toRequestBody("application/json;charset=utf-8".toMediaType())
              builder = originalRequest.newBuilder().post(body)
            } else {
              val body: RequestBody =
                Gson().toJson(map).toRequestBody("application/json;charset=utf-8".toMediaType())
              builder = originalRequest.newBuilder().post(body)
            }

          } else {
            //GET请求，在url后拼接公参
            val httpBuilder: HttpUrl.Builder = originalRequest.url.newBuilder()
            map.forEach {
              httpBuilder.addQueryParameter(it.key, it.value.toString())
            }
            builder = originalRequest.newBuilder().url(httpBuilder.build())
          }
          //处理签名sign
          var nwtime: Int = 0
          lateinit var sign: String
          val baseUrlName = originalRequest.header(CallFactoryProxy.NAME_BASE_URL_KEY)
          if(baseUrlName != null) {
            val type = SERVICE_TYPE.valueOf(baseUrlName)
            nwtime = SignUtil.getCurrentTime()
            sign = SignUtil.getTimeSign(type.signKey, nwtime)
          } else {
            nwtime = SignUtil.getCurrentTime()
            sign = SignUtil.getTimeSign(nwtime)
          }
          //添加请求头
          YtUserManager.getUserBody()?.let { builder.header("x-auth-uid", it.uid) };//uid
          YtUserManager.getUserBody()?.let { builder.header("x-access-token", it.token) };//token
          builder.header("x-auth-sign", sign)//签名认证（md5:32位16进制小写string）
          builder.header("nwtime", nwtime.toString())//当前时间戳，用于签名认证（10位）
          builder.header("app", AppUtils.getAppPackageName())//包名
          builder.header("os-version", Build.VERSION.RELEASE)//系统版本
          builder.header("device-model", Build.MODEL)//固定机型
          builder.header("udid", UUIDManager.getUUID())//设备唯一标识
          builder.header("platform", "android")//平台 ( "android"或"iphone"或"ipad" )
          builder.header(
            "version",
            AppUtils.getAppVersionName().replace("[^0-9]".toRegex(), "")
          )//版本
          builder.header("version-code", AppUtils.getAppVersionCode().toString())//版本号
          builder.header("channel", MetaDataUtils.getMetaDataInApp("UMENG_CHANNEL"))//渠道
          //生成request
          val request = builder.build()
          val nowResponse = chain.proceed(request)
          hasReLogin(nowResponse)

          nowResponse
        }).addInterceptor(logInterceptor)
      return builder.build()
    }

    //是否需要重新登录
    private fun hasReLogin(originalResponse: Response): Boolean {
      val responseBody: ResponseBody? = originalResponse.body
      val source: BufferedSource? = responseBody?.source()
      source?.request(Long.MAX_VALUE) // Buffer the entire body.

      val buffer = source?.buffer
      var charset: Charset = Charset.forName("UTF-8")
      val contentType: MediaType? = responseBody?.contentType()
      if(contentType != null) {
        charset = contentType.charset(Charset.forName("UTF-8"))!!
      }

      val bodyString = buffer?.clone()?.readString(charset)
      val response = Gson().fromJson(bodyString, BaseResponse::class.java)
      if(response.httpCode == -99) {
        if(YtUserManager.getUserBody()?.isOutSideRefreshTime() == true) {
          //这里认为是token过期，需要用户重新登录，需要发一个通知
          YtUserManager.clearUser()
          ARouteAction.User.launchLoginActivity()
          return false
        }
      } else {
        //这里认为是token失效，需要进行token刷新判断
        try {
          val s = BaseDataSource<TokenService>(null, TokenService::class.java)
            .execute({ refreshToken(YtUserManager.getUserBody()?.refreshToken) })

          val userData = YtUserManager.getUserBody()
          userData?.let {
            it.token = s.token
            it.expireTime = s.expireTime
            if(s.refreshToken.isNotEmpty()) {
              it.refreshToken = s.refreshToken
              it.refreshExpireTime = s.refreshExpireTime
            }
            YtUserManager.putUserBody(it)
            return true
          }
        } catch(e: Exception) {
          if(e is ServerCodeBadException) {
            YtUserManager.clearUser()
            ARouteAction.User.launchLoginActivity()
          }
        }
      }
      return false
    }


    //定义Headers中所使用的拼接字段
    //const val HEADER_NAME_USER = CallFactoryProxy.NAME_BASE_URL_KEY + ":" + "USER"

  }

  //设置默认的服务器地址
  override val baseUrl: String = "https://restapi.amap.com/"

  override fun createRetrofit(baseUrl: String): Retrofit {
    return Retrofit.Builder()
      .client(httpClient)
      .baseUrl(baseUrl)
      //.addConverterFactory(RxGsonConverterFactory.create())
      .callFactory(object : CallFactoryProxy(httpClient) {
        override fun getNewUrl(baseUrlName: String?, request: Request?): HttpUrl? {
          if(baseUrlName != null) {
            val type = SERVICE_TYPE.valueOf(baseUrlName)
            val oldUrl = request?.url.toString()
            val newUrl = oldUrl.replace(baseUrl, type.baseUrl)
            return newUrl.toHttpUrl();
          }
          return null
        }
      })
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  override fun showToast(msg: String) {
    ToastUtils.showShort(msg)
  }


}