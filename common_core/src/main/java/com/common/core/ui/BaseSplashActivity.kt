package com.common.core.ui

import android.content.Intent
import android.os.Bundle
import androidx.viewbinding.ViewBinding

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/8.
 */
open class BaseSplashActivity<VB : ViewBinding> : BaseActivity<VB>() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //防止退到后台后，多次点击图标启动首页问题
    if(!isTaskRoot) {
      val intent = intent
      val action = intent.action
      if(intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action != null && action == Intent.ACTION_MAIN) {
        finish()
        return
      }
    }
  }

}