package com.common.core.ui

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.Utils
import com.common.core.manager.MMKVManager
import com.common.core.manager.ARouterManager
import com.common.core.manager.TBSManager
import com.common.core.manager.UUIDManager

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/6/30.
 */
open class BaseApplication : Application() {
  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    MultiDex.install(this)
  }

  override fun onCreate() {
    super.onCreate()
    ARouterManager.init(this)
    Utils.init(this)
    UUIDManager.initRomOAID(this)
    MMKVManager.init(this)
    ActivityUtils.addActivityLifecycleCallbacks(object : Utils.ActivityLifecycleCallbacks() {
      override fun onActivityCreated(activity: Activity) {
        super.onActivityCreated(activity)
      }

      override fun onActivityDestroyed(activity: Activity) {
        super.onActivityDestroyed(activity)
      }
    })
  }
}