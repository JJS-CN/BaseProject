package com.read.bookclub.ui

import android.graphics.Color
import android.util.Log
import com.common.core.ui.BaseSplashActivity
import com.module.base.APPAction
import com.read.bookclub.databinding.ActivitySplashBinding
import com.module.base.BaseDataSource
import com.module.base.manager.YtUserManager
import com.module.base.network.BasicService
import com.module.base.router.RouteAction
import com.read.bookclub.net.AppDataSource
import com.xiaojinzi.component.anno.RouterAnno
import kotlin.math.log

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/8.
 */
@RouterAnno(hostAndPath = RouteAction.User.PATH_SPLASH)
class SplashActivity : BaseSplashActivity<ActivitySplashBinding>() {
  init {
    backgroundColor = Color.WHITE
    needAutoTitleBar = false
  }


  override fun onResume() {
    super.onResume()
    AppDataSource(null)
      .enqueue({ getAudioConf() }) {
        onSuccess {
          Log.e("eeeeeee", "回调到了Success")
        }
      }
  }


}