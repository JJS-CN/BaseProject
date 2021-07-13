package com.library.analytics

import android.app.Application
import com.library.analytics.BuildConfig
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/6.
 */
object AnalyticsManager {
  //预初始化,在application中调用
  var app: Application? = null
  fun preInit(app: Application, appKey: String, channel: String) {
    UMConfigure.preInit(app, appKey, channel)
  }

  //真正初始化方法，需要在同意隐私政策后调用
  fun init(app: Application, appKey: String, channel: String, pushSecret: String) {
    AnalyticsManager.app = app
    UMConfigure.setLogEnabled(BuildConfig.DEBUG)
    UMConfigure.init(app, appKey, channel, UMConfigure.DEVICE_TYPE_PHONE, pushSecret)
    //默认auto模式
    MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
  }

  fun onEvent(key: String) {
    MobclickAgent.onEvent(app, key)
  }

  fun onEvent(key: String, value: String) {
    MobclickAgent.onEvent(app, key, value)
  }

  fun onEvent(key: String, map: Map<String, Any>) {
    MobclickAgent.onEventObject(app, key, map)
  }
}