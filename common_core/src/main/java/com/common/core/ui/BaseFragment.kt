package com.common.core.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ToastUtils
import com.common.core.network.viewmodel.IUIActionEventObserver
import com.common.core.network.viewmodel.IViewModelActionEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.lang.reflect.ParameterizedType

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/6/30.
 */

abstract class BaseFragment<VB : ViewBinding> : Fragment(), IUIActionEventObserver {
  protected lateinit var binding: VB
  protected var rootView: View? = null

  private var isFragmentViewInit = false

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    if(rootView == null) {
      bindingView(container, javaClass)
      rootView = binding.root
    } else {
      (rootView!!.parent as? ViewGroup)?.removeView(rootView)
    }
    return rootView
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    // TODO: 2021/7/27 使用navigation仍会有重建问题 ！！！
    if(!isFragmentViewInit) {
      super.onViewCreated(view, savedInstanceState)
      isFragmentViewInit = true
      initView()
      refreshView()
      initData()
    }
  }

  @Suppress("UNCHECKED_CAST")
  private fun bindingView(container: ViewGroup?, clazz: Class<*>) {
    println("bindingFragment: 开始尝试解析：$clazz")
    if(clazz is BaseFragment<*>) {
      println("bindingFragment: 此class为Base,return")
      return
    }
    try {
      val superclass = clazz.genericSuperclass
      val aClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
      val method = aClass.getDeclaredMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.javaPrimitiveType
      )
      println("bindingFragment: 解析成功：$clazz")
      binding = method.invoke(null, layoutInflater, container, false) as VB
    } catch(e: Exception) {
      if(e is ClassCastException) {
        println("bindingFragment: ClassCastException错误，尝试遍历superclass")
        bindingView(container, clazz.superclass)
      } else {
        e.printStackTrace()
      }
    }
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    requireActivity().lifecycle.addObserver(object : DefaultLifecycleObserver {
      override fun onCreate(owner: LifecycleOwner) {
        // 这个回调会先于onCreateView触发，所以initView放在了onCreateView中
        owner.lifecycle.removeObserver(this)
        println("bindingFragment: onCreate")
      }
    })
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

  override val lContext: Context?
    get() = context
  override val lLifecycleOwner: LifecycleOwner
    get() = this.viewLifecycleOwner

  override fun showLoading(job: Job?) {
  }

  override fun dismissLoading() {
  }

  override fun showToast(msg: String?) {
    ToastUtils.showShort(msg)
  }

  override fun finishView() {
    activity?.finish()
  }

  override val lifecycleSupportedScope: CoroutineScope
    get() = this.lifecycleScope
}