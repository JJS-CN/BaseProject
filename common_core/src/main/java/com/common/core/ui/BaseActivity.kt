package com.common.core.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ToastUtils
import com.common.core.R
import com.common.core.network.viewmodel.IUIActionEventObserver
import com.common.core.network.viewmodel.IViewModelActionEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.lang.reflect.ParameterizedType

/**
 *  Class: 通过AS模板[byVB]进行触发
 *  Other:
 *  Create by jsji on  2021/6/30.
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), IUIActionEventObserver {
  protected lateinit var binding: VB

  //是否需要自动添加titlebar，同时将自动处理返回值
  protected var needAutoTitleBar = true
  //protected var needSwipeRefresh = true
  //protected var swipeRefresh: SwipeRefreshLayout? = null

  @Suppress("UNCHECKED_CAST")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    bindingView(javaClass)

  }

  fun bindingView(clazz: Class<*>) {
    println("bindingView: 开始尝试解析：$clazz")
    if(clazz is BaseActivity<*>) {
      println("bindingView: 此class为Base,return")
      return
    }
    try {
      val superclass = clazz.genericSuperclass
      val aClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
      val method = aClass.getDeclaredMethod(
        "inflate",
        LayoutInflater::class.java
      )
      binding = method.invoke(null, layoutInflater) as VB
      println("bindingView: 解析成功：$clazz")
      setContentView(getRootView())
    } catch(e: Exception) {
      if(e is ClassCastException) {
        println("bindingView: ClassCastException错误，尝试遍历superclass")
        bindingView(clazz.superclass)
      } else {
        e.printStackTrace()
      }
    }
  }

  override fun setContentView(layoutResID: Int) {
    val view = LayoutInflater.from(this).inflate(layoutResID, null)
    setContentView(view)
  }

  override fun setContentView(view: View) {
    val rootView = LinearLayout(this)
    if(needAutoTitleBar) {
      //主动附加一个titlebar
      rootView.orientation = LinearLayout.VERTICAL
      layoutInflater.inflate(R.layout.common_core_title_bar, rootView, true)
    }
    val centerView = view
    val lp = LinearLayout.LayoutParams(
      LinearLayout.LayoutParams.MATCH_PARENT,
      LinearLayout.LayoutParams.MATCH_PARENT
    )
    centerView.layoutParams = lp
    rootView.addView(centerView)
    super.setContentView(rootView)
    initView()
    beforeInitView()
    initData()
  }

  open protected fun getRootView(): View {
    return binding.root
  }

  private var centerTitleView: TextView? = null
  override fun setTitle(titleId: Int) {
    super.setTitle(titleId)
    setTitle(getString(titleId))
  }

  override fun setTitle(title: CharSequence?) {
    super.setTitle(title)
    if(centerTitleView == null) {
      centerTitleView = findViewById<TextView>(R.id.tv_title_bar_center)
    }
    centerTitleView?.text = title
  }

  open fun initView() {}
  private fun beforeInitView() {
    findViewById<View>(R.id.iv_title_bar_back)?.setOnClickListener {
      onBackPressed()
    }
  }

  open fun initData() {}


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

  override fun showToast(msg: String) {
    if(msg.isNotBlank()) {
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