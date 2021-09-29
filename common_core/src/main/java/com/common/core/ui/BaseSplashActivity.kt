package com.common.core.ui

import android.content.Intent
import android.os.Bundle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.viewbinding.ViewBinding

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/8.
 */
open abstract class BaseSplashActivity : BaseActivity() {
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
    onSplash(savedInstanceState)
  }

  /**
   * 在这里执行业务逻辑代码
   */
  abstract fun onSplash(savedInstanceState: Bundle?)

}