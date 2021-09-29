package com.common.core.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ToastUtils
import com.common.core.R
import com.common.core.network.viewmodel.IUIActionEventObserver
import com.common.core.network.viewmodel.IViewModelActionEvent
import com.common.core.widget.AppTitleBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 *  Class: 通过AS模板[byVB]进行触发
 *  Other:
 *  Create by jsji on  2021/6/30.
 */
abstract class BaseActivity : AppCompatActivity(), IUIActionEventObserver {
  // TODO: 2021/7/30 页面需要loading、error、nothing方法，但应该抽离出去，fragment可以复用
  companion object {
    var defaultBackgroundColor: Int? = null
  }



  //是否需要自动添加titlebar，同时将自动处理返回值
  protected var backgroundColor: Int? = null

  @Suppress("UNCHECKED_CAST")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if(backgroundColor != null) {
      window.decorView.findViewById<View>(android.R.id.content)
        .setBackgroundColor(backgroundColor!!)
    } else if(defaultBackgroundColor != null) {
      window.decorView.findViewById<View>(android.R.id.content)
        .setBackgroundColor(defaultBackgroundColor!!)
    }
    initView()
    refreshView()
    initData()
  }



  open fun initView() {}

  open fun initData() {}
  open fun refreshView() {}


  //网络请求相关
  protected inline fun <reified VM> getViewModel(
    factory: ViewModelProvider.Factory? = null,
    noinline initializer: (VM.(lifecycleOwner: LifecycleOwner) -> Unit)? = null
  ): Lazy<VM> where VM : ViewModel, VM : IViewModelActionEvent {
    return getViewModel(VM::class.java, factory, initializer)
  }

  override val lifecycleSupportedScope: CoroutineScope
    get() = lifecycleScope

  override val lContext: Context?
    get() = this

  override val lLifecycleOwner: LifecycleOwner
    get() = this


  override fun showLoading(job: Job?) {
    dismissLoading()
  }

  override fun dismissLoading() {
    //swipeRefresh!!.isRefreshing = false
  }

  override fun showToast(msg: String?) {
    if(msg?.isNotBlank() == true) {
      ToastUtils.showShort(msg)
    }
  }

  override fun finishView() {
    finish()
  }

  override fun onDestroy() {
    super.onDestroy()
    dismissLoading()
  }
}