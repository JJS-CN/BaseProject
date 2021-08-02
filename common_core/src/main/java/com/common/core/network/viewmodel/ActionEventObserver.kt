package com.common.core.network.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.blankj.utilcode.util.Utils
import com.common.core.network.coroutine.ICoroutineEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @Author: leavesC
 * @Date: 2020/4/30 15:23
 * @Desc: 用于定义 View 和  ViewModel 均需要实现的一些 UI 层行为
 * @GitHub：https://github.com/leavesC
 */
interface IUIActionEvent : ICoroutineEvent {

  fun showLoading(job: Job?)

  fun dismissLoading()

  fun showToast(msg: String?)

  fun finishView()

}

interface IViewModelActionEvent : IUIActionEvent {

  val showLoadingEventLD: MutableLiveData<ShowLoadingEvent>

  val dismissLoadingEventLD: MutableLiveData<DismissLoadingEvent>

  val showToastEventLD: MutableLiveData<ShowToastEvent>

  val finishViewEventLD: MutableLiveData<FinishViewEvent>

  override fun showLoading(job: Job?) {
    showLoadingEventLD.value = ShowLoadingEvent(job)
  }

  override fun dismissLoading() {
    dismissLoadingEventLD.value = DismissLoadingEvent
  }

  fun showToast(resId: Int) {
    showToast(Utils.getApp().getString(resId))
  }

  override fun showToast(msg: String?) {
    showToastEventLD.value = ShowToastEvent(msg)
  }

  override fun finishView() {
    finishViewEventLD.value = FinishViewEvent
  }

}

interface IUIActionEventObserver : IUIActionEvent {

  val lContext: Context?

  val lLifecycleOwner: LifecycleOwner

  fun <VM> getViewModel(
    clazz: Class<VM>,
    factory: ViewModelProvider.Factory? = null,
    initializer: (VM.(lifecycleOwner: LifecycleOwner) -> Unit)? = null
  ): Lazy<VM> where VM : ViewModel, VM : IViewModelActionEvent {
    return lazy {
      getViewModelFast(clazz, factory, initializer)
    }
  }

  fun <VM> getViewModelFast(
    clazz: Class<VM>,
    factory: ViewModelProvider.Factory? = null,
    initializer: (VM.(lifecycleOwner: LifecycleOwner) -> Unit)? = null
  ): VM where VM : ViewModel, VM : IViewModelActionEvent {
    return when(val localValue = lLifecycleOwner) {
      is ViewModelStoreOwner -> {
        if(factory == null) {
          ViewModelProvider(localValue).get(clazz)
        } else {
          ViewModelProvider(localValue, factory).get(clazz)
        }
      }
      else -> {
        factory?.create(clazz) ?: clazz.newInstance()
      }
    }.apply {
      generateActionEvent(this)
      initializer?.invoke(this, lLifecycleOwner)
    }
  }

  /**
   * 注册livedata的好处是，在未显示页面由于定时器等原因触发接口时，不会乱展示toast和loading给用户
   */
  fun <VM> generateActionEvent(viewModel: VM) where VM : ViewModel, VM : IViewModelActionEvent {
    viewModel.showLoadingEventLD.observe(lLifecycleOwner, Observer {
      this@IUIActionEventObserver.showLoading(it.job)
    })
    viewModel.dismissLoadingEventLD.observe(lLifecycleOwner, Observer {
      this@IUIActionEventObserver.dismissLoading()
    })
    viewModel.showToastEventLD.observe(lLifecycleOwner, Observer {
      if(!it.message.isNullOrEmpty()) {
        this@IUIActionEventObserver.showToast(it.message)
      }
    })
    viewModel.finishViewEventLD.observe(lLifecycleOwner, Observer {
      this@IUIActionEventObserver.finishView()
    })
  }

}