package com.common.core.network.datasource

import android.util.LruCache
import com.common.core.BuildConfig
import com.common.core.network.base.BaseUrlCallFactory
import com.common.core.network.callback.RequestCallback
import com.common.core.network.coroutine.ICoroutineEvent
import com.common.core.network.exception.ApiException
import com.common.core.network.viewmodel.IUIActionEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.GlobalScope
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

/**
 * @Author: leavesC
 * @Date: 2020/5/4 0:56
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
abstract class BaseRemoteDataSource<Api : Any>(
  protected val iUiActionEvent: IUIActionEvent?,
  protected val apiServiceClass: Class<Api>
) : ICoroutineEvent {

  companion object {

    /**
     * ApiService 缓存
     */
    private val apiServiceCache = LruCache<String, Any>(30)

    /**
     * Retrofit 缓存
     */
    private val retrofitCache = LruCache<String, Retrofit>(3)

    /**
     * 默认的 OKHttpClient
     */
    private val defaultOkHttpClient by lazy {
      OkHttpClient.Builder()
        .readTimeout(10000L, TimeUnit.MILLISECONDS)
        .writeTimeout(10000L, TimeUnit.MILLISECONDS)
        .connectTimeout(10000L, TimeUnit.MILLISECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
          //日志等级
          if(BuildConfig.DEBUG) {
            setLevel(HttpLoggingInterceptor.Level.BODY)
          } else {
            setLevel(HttpLoggingInterceptor.Level.NONE)
          }
        })
        .retryOnConnectionFailure(true).build()
    }

    /**
     * 构建默认的 Retrofit
     */
    private fun createDefaultRetrofit(baseUrl: String, callFactory: Call.Factory): Retrofit {
      return Retrofit.Builder()
        .client(defaultOkHttpClient)
        .baseUrl(baseUrl)
        .callFactory(callFactory)
        .addConverterFactory(RxGsonConverterFactory.create())
        .build()
    }

  }

  /**
   * 通过重写createCallFactory 并调用addBaseUrl来
   */
  protected open fun createCallFactory(): BaseUrlCallFactory {
    return BaseUrlCallFactory(baseUrl, defaultOkHttpClient)
  }

  /**
   * 和生命周期绑定的协程作用域
   */
  override val lifecycleSupportedScope = iUiActionEvent?.lifecycleSupportedScope ?: GlobalScope

  /**
   * 由子类实现此字段以便获取 baseUrl
   */
  protected abstract val baseUrl: String

  /**
   * 允许子类自己来实现创建 Retrofit 的逻辑
   * 外部无需缓存 Retrofit 实例，ReactiveHttp 内部已做好缓存处理
   * 但外部需要自己判断是否需要对 OKHttpClient 进行缓存
   * @param baseUrl
   */
  protected open fun createRetrofit(baseUrl: String): Retrofit {
    return createDefaultRetrofit(baseUrl, createCallFactory())
  }

  protected open fun generateBaseUrl(baseUrl: String): String {
    if(baseUrl.isNotBlank()) {
      return baseUrl
    }
    return this.baseUrl
  }

  /**
   * 根据baseUrl不同，会从缓存中获取retrofit实例，或者进行新增。
   */
  fun getApiService(baseUrl: String = ""): Api {
    return getApiService(generateBaseUrl(baseUrl), apiServiceClass)
  }

  private fun getApiService(baseUrl: String, apiServiceClazz: Class<Api>): Api {
    val key = apiServiceClass.canonicalName
    val get = apiServiceCache.get(key)?.let {
      it as? Api
    }
    if(get != null) {
      return get
    }
    val retrofit = retrofitCache.get(baseUrl) ?: (createRetrofit(baseUrl).apply {
      retrofitCache.put(baseUrl, this)
    })
    val apiService = retrofit.create(apiServiceClazz)
    apiServiceCache.put(key, apiService)
    return apiService
  }

  protected fun handleException(throwable: Throwable, callback: RequestCallback<*>?) {
    if(callback == null) {
      return
    }
    if(throwable is CancellationException) {
      callback.onCancelled?.invoke()
      return
    }
    if(throwable is ApiException) {
      callback.onApiError?.invoke(throwable)
    }
    callback.onError?.invoke(throwable)
    if(callback.needFailToast()) {
      val error = exceptionFormat(throwable)
      if(error.isNotBlank()) {
        iUiActionEvent?.showToast(error)
      }
    }
  }


  /**
   * 用于对 BaseException 进行格式化，以便在请求失败时 Toast 提示错误信息
   */
  protected open fun exceptionFormat(throwable: Throwable): String {
    return when(throwable) {
      is ApiException -> {
        throwable.errorMessage
      }
      is ConnectException, is SocketTimeoutException, is UnknownHostException -> {
        "连接超时，请检查您的网络设置"
      }
      else -> {
        "请求异常"
      }
    }
  }


}