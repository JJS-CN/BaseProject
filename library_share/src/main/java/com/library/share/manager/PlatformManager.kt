package com.library.share.manager

import com.umeng.socialize.PlatformConfig

/**
 *  Class:
 *  Other:分享与第三方登录需要注册对应平台的appid
 *  Create by jsji on  2021/7/7.
 */
object PlatformManager {
  fun initWeixin(appId: String, appKey: String, provider: String) {
    PlatformConfig.setWeixin(appId, appKey)
    PlatformConfig.setWXFileProvider(provider)
  }

  fun initQQZone(appId: String, appKey: String, provider: String) {
    PlatformConfig.setQQZone(appId, appKey)
    PlatformConfig.setQQFileProvider(provider)
  }

  fun initSina(appId: String, appKey: String, redirectUrl: String, provider: String) {
    PlatformConfig.setSinaWeibo(appId, appKey, redirectUrl)
    PlatformConfig.setSinaFileProvider(provider)
  }
}