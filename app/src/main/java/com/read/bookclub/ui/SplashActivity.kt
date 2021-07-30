package com.read.bookclub.ui

import android.graphics.Color
import com.common.core.ui.BaseSplashActivity
import com.module.base.APPAction
import com.read.bookclub.databinding.ActivitySplashBinding
import com.module.base.BaseDataSource
import com.module.base.manager.YtUserManager
import com.module.base.network.BasicService
import com.module.base.router.RouteAction
import com.read.bookclub.net.AppDataSource
import com.xiaojinzi.component.anno.RouterAnno

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
    binding.btnLogin.setOnClickListener {
      RouteAction.User.launchLoginActivity()
    }
    binding.btnRegister.setOnClickListener {
      RouteAction.User.launchRegisterActivity()
    }
    if(YtUserManager.isLogin()) {
      //已登录时，执行token刷新
      BaseDataSource<BasicService>(null, BasicService::class.java)
        .enqueue({ refreshToken(YtUserManager.getUserBody().refreshToken) }) {
          onSuccess {
            it?.let {
              val userData = YtUserManager.getUserBody()
              userData.token = it.token
              YtUserManager.putUserBody(userData)
              toNext()
            }
          }
          onError {
            YtUserManager.clearUser()
          }
        }
    }
  }

  private fun toNext() {
    AppDataSource(null)
      .enqueue({ getAudioConf() }) {
        onSuccess {
          it?.let {
            APPAction.appConfig = it
          }
        }
      }
    RouteAction.APP.goHome()
  }


}