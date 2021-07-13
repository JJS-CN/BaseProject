package com.common.core.manager

import android.app.Application
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.BuildConfig
import kotlin.math.log

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/6/30.
 */
object ARouterManager {
  fun init(app: Application) {
    //初始化路由
    if(BuildConfig.DEBUG) {
      Log.e("ARouterManager", "openDebug")
      ARouter.openLog()
      ARouter.openDebug()
    }
    ARouter.init(app)
  }
}