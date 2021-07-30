package com.common.core.network.datasource

import android.util.Log
import com.common.core.network.bean.IHttpWrapBean
import com.common.core.network.callback.RequestCallback
import com.common.core.network.exception.BaseHttpException
import com.common.core.network.exception.ServerCodeBadException
import com.common.core.network.viewmodel.IUIActionEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

/**
 * @Author: leavesC
 * @Date: 2020/5/4 0:55
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
abstract class RemoteDataSource<Api : Any>(
  iUiActionEvent: IUIActionEvent?,
  apiServiceClass: Class<Api>
) : BaseRemoteDataSource<Api>(iUiActionEvent, apiServiceClass) {
  private val TAG = "RemoteDataSource"
  fun <Data> enqueueLoading(
    apiFun: suspend Api.() -> IHttpWrapBean<Data>,
    baseUrl: String = "",
    callbackFun: (RequestCallback<Data>.() -> Unit)? = null
  ): Job {
    return enqueue(
      apiFun = apiFun,
      showLoading = true,
      baseUrl = baseUrl,
      callbackFun = callbackFun
    )
  }

  // TODO: 2021/7/28  无法在子线程启动！！！！！！！！！
  fun <Data> enqueue(
    apiFun: suspend Api.() -> IHttpWrapBean<Data>,
    showLoading: Boolean = false,
    baseUrl: String = "",
    callbackFun: (RequestCallback<Data>.() -> Unit)? = null
  ): Job {
    return launchMain {
      val callback = if(callbackFun == null) {
        null
      } else {
        RequestCallback<Data>().apply {
          callbackFun.invoke(this)
        }
      }
      try {
        if(showLoading) {
          showLoading(coroutineContext[Job])
        }
        callback?.onStart?.invoke()
        val response: IHttpWrapBean<Data>
        try {
          response = apiFun.invoke(getApiService(baseUrl))
          if(!response.httpIsSuccess) {
            throw ServerCodeBadException(response)
          }
        } catch(throwable: Throwable) {
          handleException(throwable, callback)
          return@launchMain
        }
        onGetResponse(callback, response.httpData)
      } finally {
        try {
          callback?.onFinally?.invoke()
        } finally {
          if(showLoading) {
            dismissLoading()
          }
        }
      }
    }
  }
  fun <Data> enqueueG(
    apiFun: suspend Api.() -> IHttpWrapBean<Data>,
    showLoading: Boolean = false,
    baseUrl: String = "",
    callbackFun: (RequestCallback<Data>.() -> Unit)? = null
  ): Job {
    return launchMainG {
      val callback = if(callbackFun == null) {
        null
      } else {
        RequestCallback<Data>().apply {
          callbackFun.invoke(this)
        }
      }
      try {
        if(showLoading) {
          showLoading(coroutineContext[Job])
        }
        callback?.onStart?.invoke()
        val response: IHttpWrapBean<Data>
        try {
          response = apiFun.invoke(getApiService(baseUrl))
          if(!response.httpIsSuccess) {
            throw ServerCodeBadException(response)
          }
        } catch(throwable: Throwable) {
          handleException(throwable, callback)
          return@launchMainG
        }
        onGetResponse(callback, response.httpData)
      } finally {
        try {
          callback?.onFinally?.invoke()
        } finally {
          if(showLoading) {
            dismissLoading()
          }
        }
      }
    }
  }

  fun <Data> enqueueOriginLoading(
    apiFun: suspend Api.() -> Data,
    baseUrl: String = "",
    callbackFun: (RequestCallback<Data>.() -> Unit)? = null
  ): Job {
    return enqueueOrigin(
      apiFun = apiFun,
      showLoading = true,
      baseUrl = baseUrl,
      callbackFun = callbackFun
    )
  }

  fun <Data> enqueueOrigin(
    apiFun: suspend Api.() -> Data,
    showLoading: Boolean = false,
    baseUrl: String = "",
    callbackFun: (RequestCallback<Data>.() -> Unit)? = null
  ): Job {
    return launchMain {
      val callback = if(callbackFun == null) {
        null
      } else {
        RequestCallback<Data>().apply {
          callbackFun.invoke(this)
        }
      }
      try {
        if(showLoading) {
          showLoading(coroutineContext[Job])
        }
        callback?.onStart?.invoke()
        val response: Data
        try {
          response = apiFun.invoke(getApiService(baseUrl))
        } catch(throwable: Throwable) {
          handleException(throwable, callback)
          return@launchMain
        }
        onGetResponse(callback, response)
      } finally {
        try {
          callback?.onFinally?.invoke()
        } finally {
          if(showLoading) {
            dismissLoading()
          }
        }
      }
    }
  }

  private suspend fun <Data> onGetResponse(callback: RequestCallback<Data>?, httpData: Data) {
    callback?.let {
      withNonCancellable {
        callback.onSuccess?.let {
          withMain {
            //如果值为空，invoke动态代理将会报错。因为定义的success接收值中是非空的。
            try {
              it.invoke(httpData)
            } catch(e: Exception) {
              Log.e(TAG, "invoke错误：" + e.message)
              e.printStackTrace()
            }
          }
        }
        callback.onSuccessIO?.let {
          withIO {
            try {
              //如果值为空，invoke动态代理将会报错。因为定义的success接收值中是非空的。
              it.invoke(httpData)
            } catch(e: Exception) {
              Log.e(TAG, "invoke错误：" + e.message)
              e.printStackTrace()
            }
          }
        }
      }
    }
  }

  /**
   * 同步请求，可能会抛出异常，外部需做好捕获异常的准备
   * @param apiFun
   */
  @Throws(BaseHttpException::class)
  fun <Data> execute(apiFun: suspend Api.() -> IHttpWrapBean<Data>, baseUrl: String = ""): Data {
    return runBlocking {
      try {
        val asyncIO = asyncIO {
          apiFun.invoke(getApiService(baseUrl))
        }
        val response = asyncIO.await()
        if(response.httpIsSuccess) {
          return@runBlocking response.httpData
        }
        throw ServerCodeBadException(response)
      } catch(throwable: Throwable) {
        throw generateBaseExceptionReal(throwable)
      }
    }
  }

}