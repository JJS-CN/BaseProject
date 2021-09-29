package com.read.bookclub.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.common.core.ui.BaseSplashActivity
import com.common.core.widget.AppTitleBar
import com.module.base.router.RouteAction
import com.xiaojinzi.component.anno.RouterAnno

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/8.
 */
@RouterAnno(hostAndPath = RouteAction.User.PATH_SPLASH)
class SplashActivity : BaseSplashActivity() {

  override fun onSplash(savedInstanceState: Bundle?) {
    setContent {
      MaterialTheme() {
        AppTitleBar(activity = this, title = "123")
      }
    }

  }

}
