package com.common.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/6/30.
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {
  protected lateinit var binding: VB
  protected lateinit var rootView: View
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val superclass = javaClass.genericSuperclass
    val aClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
    try {
      val method = aClass.getDeclaredMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.javaPrimitiveType
      )
      binding = method.invoke(null, layoutInflater, container, false) as VB
    } catch(e: Exception) {
      e.printStackTrace()
    }
    rootView = binding.root
    return rootView
  }
}