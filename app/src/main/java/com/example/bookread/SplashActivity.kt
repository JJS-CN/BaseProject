package com.example.bookread

import android.content.Intent
import com.common.core.ui.BaseSplashActivity
import com.example.bookread.databinding.ActivitySplashBinding
import com.module.base.manager.YtUserManager
import com.module.base.router.ARouteAction
import com.module.user.ui.LoginActivity

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/8.
 */
class SplashActivity : BaseSplashActivity<ActivitySplashBinding>() {
  override fun onResume() {
    super.onResume()
    toLogin()
  }

  fun toLogin() {
    if(YtUserManager.isLogin()) {
      ARouteAction.APP.goHome()
    } else {
      ARouteAction.User.launchLoginActivity()
    }
  }

}