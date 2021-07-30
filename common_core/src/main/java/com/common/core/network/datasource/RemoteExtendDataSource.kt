package com.common.core.network.datasource

import com.common.core.network.bean.IHttpWrapBean
import com.common.core.network.callback.RequestPairCallback
import com.common.core.network.callback.RequestTripleCallback
import com.common.core.network.exception.ServerCodeBadException
import com.common.core.network.viewmodel.IUIActionEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

/**
 * @Author: leavesC
 * @Date: 2020/5/4 0:55
 * @Desc:
 * 提供了 两个/三个 接口同时并发请求的方法
 * 当所有接口都请求成功时，会通过 onSuccess 方法传出请求结果
 * 当包含的某个接口请求失败时，则会直接回调 onFail 方法
 * @GitHub：https://github.com/leavesC
 */
abstract class RemoteExtendDataSource<Api : Any>(
  // TODO: 2021/7/30 但。。有4个的时候怎么办！！！
  iActionEvent: IUIActionEvent?,
  apiServiceClass: Class<Api>
) : RemoteDataSource<Api>(iActionEvent, apiServiceClass) {

  fun <DataA, DataB> enqueueLoading(
    apiFunA: suspend Api.() -> IHttpWrapBean<DataA>,
    apiFunB: suspend Api.() -> IHttpWrapBean<DataB>,
    callbackFun: (RequestPairCallback<DataA, DataB>.() -> Unit)? = null
  ): Job {
    return enqueue(
      apiFunA = apiFunA,
      apiFunB = apiFunB,
      showLoading = true,
      callbackFun = callbackFun
    )
  }

  fun <DataA, DataB> enqueue(
    apiFunA: suspend Api.() -> IHttpWrapBean<DataA>,
    apiFunB: suspend Api.() -> IHttpWrapBean<DataB>,
    showLoading: Boolean = false,
    callbackFun: (RequestPairCallback<DataA, DataB>.() -> Unit)? = null
  ): Job {
    return launchMain {
      val callback = if(callbackFun == null) {
        null
      } else {
        RequestPairCallback<DataA, DataB>().apply {
          callbackFun.invoke(this)
        }
      }
      try {
        if(showLoading) {
          showLoading(coroutineContext[Job])
        }
        callback?.onStart?.invoke()
        val responseList: List<IHttpWrapBean<out Any?>>
        try {
          responseList = listOf(
            lifecycleSupportedScope.async { apiFunA.invoke(getApiService()) },
            lifecycleSupportedScope.async { apiFunB.invoke(getApiService()) }
          ).awaitAll()
          val failed = responseList.find { !it.httpIsSuccess }
          if(failed != null) {
            throw ServerCodeBadException(failed)
          }
        } catch(throwable: Throwable) {
          handleException(throwable, callback)
          return@launchMain
        }
        onGetResponse(callback, responseList)
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

  private suspend fun <DataA, DataB> onGetResponse(
    callback: RequestPairCallback<DataA, DataB>?,
    responseList: List<IHttpWrapBean<out Any?>>
  ) {
    callback?.let {
      withNonCancellable {
        callback.onSuccess?.let {
          withMain {
            it.invoke(
              responseList[0].httpData as DataA,
              responseList[1].httpData as DataB
            )
          }
        }
        callback.onSuccessIO?.let {
          withIO {
            it.invoke(
              responseList[0].httpData as DataA,
              responseList[1].httpData as DataB
            )
          }
        }
      }
    }
  }

  fun <DataA, DataB, DataC> enqueueLoading(
    apiFunA: suspend Api.() -> IHttpWrapBean<DataA>,
    apiFunB: suspend Api.() -> IHttpWrapBean<DataB>,
    apiFunC: suspend Api.() -> IHttpWrapBean<DataC>,
    baseUrl: String = "",
    callbackFun: (RequestTripleCallback<DataA, DataB, DataC>.() -> Unit)? = null
  ): Job {
    return enqueue(
      apiFunA = apiFunA,
      apiFunB = apiFunB,
      apiFunC = apiFunC,
      showLoading = true,
      baseUrl = baseUrl,
      callbackFun = callbackFun
    )
  }

  fun <DataA, DataB, DataC> enqueue(
    apiFunA: suspend Api.() -> IHttpWrapBean<DataA>,
    apiFunB: suspend Api.() -> IHttpWrapBean<DataB>,
    apiFunC: suspend Api.() -> IHttpWrapBean<DataC>,
    showLoading: Boolean = false,
    baseUrl: String = "",
    callbackFun: (RequestTripleCallback<DataA, DataB, DataC>.() -> Unit)? = null
  ): Job {
    return launchMain {
      val callback = if(callbackFun == null) {
        null
      } else {
        RequestTripleCallback<DataA, DataB, DataC>().apply {
          callbackFun.invoke(this)
        }
      }
      try {
        if(showLoading) {
          showLoading(coroutineContext[Job])
        }
        val responseList: List<IHttpWrapBean<out Any?>>
        try {
          responseList = listOf(
            lifecycleSupportedScope.async { apiFunA.invoke(getApiService(baseUrl)) },
            lifecycleSupportedScope.async { apiFunB.invoke(getApiService(baseUrl)) },
            lifecycleSupportedScope.async { apiFunC.invoke(getApiService(baseUrl)) }
          ).awaitAll()
          val failed = responseList.find { !it.httpIsSuccess }
          if(failed != null) {
            throw ServerCodeBadException(failed)
          }
        } catch(throwable: Throwable) {
          handleException(throwable, callback)
          return@launchMain
        }
        onGetResponse(callback, responseList)
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

  private suspend fun <DataA, DataB, DataC> onGetResponse(
    callback: RequestTripleCallback<DataA, DataB, DataC>?,
    responseList: List<IHttpWrapBean<out Any?>>
  ) {
    callback?.let {
      withNonCancellable {
        callback.onSuccess?.let {
          withMain {
            it.invoke(
              responseList[0].httpData as DataA,
              responseList[1].httpData as DataB,
              responseList[2].httpData as DataC
            )
          }
        }
        callback.onSuccessIO?.let {
          withIO {
            it.invoke(
              responseList[0].httpData as DataA,
              responseList[1].httpData as DataB,
              responseList[2].httpData as DataC
            )
          }
        }
      }
    }
  }

}