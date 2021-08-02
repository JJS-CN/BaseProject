package com.module.base

import android.os.Build
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.MetaDataUtils
import com.common.core.network.base.BaseLoggingInterceptor
import com.common.core.network.base.BaseUrlCallFactory
import com.common.core.network.viewmodel.IUIActionEvent
import com.common.core.manager.UUIDManager
import com.common.core.network.datasource.RemoteDataSource
import okhttp3.*
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
  RemoteDataSource<Api>(iActionEvent, apiServiceClass) {
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
            /*if(originalRequest.body is FormBody) {
              val formBody = originalRequest.body as FormBody
              // 先整理出apiService传递过来的参数
              //然后再进行公参的添加
              map.putAll(formBody.toParamsMap())
              val body: RequestBody =
                gson.toJson(map).toRequestBody("application/json;charset=utf-8".toMediaType())
              builder = originalRequest.newBuilder().post(body)
            } else {
              val body: RequestBody =
                gson.toJson(map).toRequestBody("application/json;charset=utf-8".toMediaType())
              builder = originalRequest.newBuilder().post(body)
            }*/
            builder = originalRequest.newBuilder()
          } else {
            //GET请求，在url后拼接公参
            val httpBuilder: HttpUrl.Builder = originalRequest.url.newBuilder()
            map.forEach {
              httpBuilder.addQueryParameter(it.key, it.value.toString())
            }
            builder = originalRequest.newBuilder().url(httpBuilder.build())
          }
          //添加请求头
          builder.header("app", AppUtils.getAppPackageName())//包名
          builder.header("os-version", Build.VERSION.RELEASE)//系统版本
          builder.header("device-model", Build.MODEL)//固定机型
          builder.header("udid", UUIDManager.getUUID())//设备唯一标识
          builder.header("platform", "android")//平台 ( "android"或"iphone"或"ipad" )
          builder.header(
            "version",
            AppUtils.getAppVersionName().replace("[^0-9.]".toRegex(), "")
          )//版本
          builder.header("version-code", AppUtils.getAppVersionCode().toString())//版本号
          builder.header("channel", MetaDataUtils.getMetaDataInApp("UMENG_CHANNEL"))//渠道
          //生成request
          val request = builder.build()
          val nowResponse = chain.proceed(request)


          nowResponse
        }).addInterceptor(logInterceptor)
      return builder.build()
    }


  }

  //设置默认的服务器地址
  override val baseUrl: String = "https://www.baidu.com/"

  override fun createCallFactory(): BaseUrlCallFactory {
    return super.createCallFactory().apply {
      addServiceUrlPair("USER", "https://restapi.amap.com/")
    }
  }


}