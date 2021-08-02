package com.common.core.network.callback

import android.util.Log
import com.common.core.network.exception.ApiException

/**
 * @Author: leavesC
 * @Date: 2020/5/4 0:44
 * @Desc: Callback
 * @GitHub：https://github.com/leavesC
 */
open class RequestCallback<Data>(
  internal var onStart: (() -> Unit)? = null,
  internal var onSuccess: ((Data) -> Unit)? = null,
  internal var onSuccessIO: (suspend (Data) -> Unit)? = null,
  internal var onCancelled: (() -> Unit)? = null,
  internal var onError: ((Throwable) -> Unit)? = null,
  internal var onApiError: ((ApiException) -> Unit)? = null,
  internal var onFinally: (() -> Unit)? = null,

  internal var needFailToast: (() -> Boolean) = { true },
  internal var needLoading: (() -> Boolean) = { false },
  internal var needRunGlobal: (() -> Boolean) = { false },
) {

  /**
   * 在显示 Loading 之后且开始网络请求之前执行
   */
  fun onStart(block: () -> Unit) {
    this.onStart = block
  }

  /**
   * 如果外部主动取消了网络请求，不会回调 onFail，而是回调此方法，随后回调 onFinally
   * 但如果当取消网络请求时已回调了 onSuccess / onSuccessIO 方法，则不会回调此方法
   */
  fun onCancelled(block: () -> Unit) {
    this.onCancelled = block
  }

  /**
   * 当网络请求成功时会调用此方法，随后会先后调用 onSuccessIO、onFinally 方法
   * 为空时会invoke出错，所以需要保证后台返回值的正确性
   */
  fun onSuccess(block: (data: Data) -> Unit) {
    this.onSuccess = block
  }

  /**
   * 在 onSuccess 方法之后，onFinally 方法之前执行
   * 考虑到网络请求成功后有需要将数据保存到数据库之类的耗时需求，所以提供了此方法用于在 IO 线程进行执行
   * 注意外部不要在此处另开子线程，此方法会等到耗时任务完成后再执行 onFinally 方法
   * 为空时也应执行
   */
  fun onSuccessIO(block: suspend (Data) -> Unit) {
    this.onSuccessIO = block
  }

  /**
   * 返回服务器错误，在onError之前执行
   */
  fun onApiError(block: (ApiException) -> Unit) {
    this.onApiError = block
  }

  /**
   * 当网络请求失败时会调用此方法，在 onFinally 被调用之前执行
   */
  fun onError(block: (Throwable) -> Unit) {
    this.onError = block
  }


  /**
   * 在网络请求结束之后（不管请求成功与否）且隐藏 Loading 之前执行
   */
  fun onFinally(block: () -> Unit) {
    this.onFinally = block
  }

  /**
   * 用于控制是否当网络请求失败时 Toast 失败原因
   * 默认为 true，即进行 Toast 提示
   */
  fun needFailToast(block: () -> Boolean) {
    this.needFailToast = block
  }

  /**
   * 用于控制当前请求是否需要显示loading窗
   */
  fun needLoading(block: () -> Boolean) {
    this.needLoading = block
  }

  /**
   * 在全局GlobalScope中运行，防止由于生命周期异常导致的接口未调用
   */
  fun needRunGlobal(block: () -> Boolean) {
    this.needRunGlobal = block
  }

}