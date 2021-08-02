package com.common.core.network.datasource

import android.util.Log
import com.common.core.network.bean.IHttpWrapBean
import com.common.core.network.callback.RequestCallback
import com.common.core.network.exception.ApiException
import com.common.core.network.viewmodel.IUIActionEvent
import kotlinx.coroutines.*

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

  // TODO: 2021/7/28  无法在子线程启动！！！！！！！！！
  fun <Data> enqueue(
    apiFun: suspend Api.() -> IHttpWrapBean<Data>,
    baseUrl: String = "",
    callbackFun: (RequestCallback<Data>.() -> Unit)? = null
  ): Job {
    val callback = if(callbackFun == null) {
      null
    } else {
      RequestCallback<Data>().apply {
        callbackFun.invoke(this)
      }
    }
    val needRunGlobal = callback?.let { it.needRunGlobal() }
    val scope = if(needRunGlobal == true) {
      GlobalScope
    } else {
      lifecycleSupportedScope
    }
    return scope.launch {

      val needLoading = callback?.let { it.needLoading() }
      try {
        if(needLoading == true) {
          iUiActionEvent?.showLoading(coroutineContext[Job])
        }
        callback?.onStart?.invoke()
        val response: IHttpWrapBean<Data>
        try {
          response = apiFun.invoke(getApiService(baseUrl))
          if(!response.httpIsSuccess) {
            throw ApiException(response.httpCode, response.httpMsg)
          }
        } catch(throwable: Throwable) {
          handleException(throwable, callback)
          return@launch
        }
        onGetResponse(callback, response.httpData)
      } finally {
        try {
          callback?.onFinally?.invoke()
        } finally {
          if(needLoading == true) {
            iUiActionEvent?.dismissLoading()
          }
        }
      }
    }
  }

  private suspend fun <Data> onGetResponse(callback: RequestCallback<Data>?, httpData: Data) {
    callback?.let {
      withContext(NonCancellable) {
        callback.onSuccess?.let {
          withContext(Dispatchers.Main) {
            //如果值为空，invoke动态代理将会报错。因为定义的success接收值中是非空的。
            //所以在data可能为空、或者不关心回调内容时，需要设置BaseResponse<*> 、 BaseResponse<Any?>
            //这里的* 和Any? 是一样的效果
            try {
              it.invoke(httpData)
            } catch(e: Exception) {
              Log.e(TAG, "invoke错误：" + e.message)
              e.printStackTrace()
            }
          }
        }
        callback.onSuccessIO?.let {
          withContext(Dispatchers.IO) {
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
  // TODO: 2021/8/1 这里最好包装以下，不然外部catch处理也麻烦，模板代码
  @Throws(ApiException::class)
  fun <Data> execute(apiFun: suspend Api.() -> IHttpWrapBean<Data>): Data {
    return runBlocking {
      try {

        val asyncIO = lifecycleSupportedScope.async(Dispatchers.IO) {
          apiFun.invoke(getApiService(baseUrl))
        }
        val response = asyncIO.await()
        if(response.httpIsSuccess) {
          return@runBlocking response.httpData
        }
        throw ApiException(response.httpCode, response.httpMsg)
      } catch(throwable: Throwable) {
        throw throwable
      }
    }
  }

}