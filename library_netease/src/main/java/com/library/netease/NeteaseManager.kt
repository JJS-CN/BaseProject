package com.library.netease

import android.app.Application
import com.library.netease.listener.OnlineStatusListener
import com.netease.nimlib.sdk.*
import com.netease.nimlib.sdk.auth.LoginInfo


/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/16.
 */
object NeteaseManager {
  //预初始化，只配置
  fun config(app: Application, appkey: String, imId: String?, imToken: String?) {
    var loginInfo: LoginInfo? = null
    if(!imId.isNullOrEmpty() && !imToken.isNullOrEmpty()) {
      loginInfo = LoginInfo(imId, imToken)
    }
    val options = SDKOptions()
    options.asyncInitSDK = true
    options.reducedIM = true
    options.appKey = appkey
    if(app.externalCacheDir?.exists() != true) {
      app.externalCacheDir?.mkdirs()
    }
    options.sdkStorageRootPath = app.externalCacheDir.toString()
    NIMClient.config(app, loginInfo, options)
  }

  //真正初始化，在UI线程上按需初始化，但不能在application的onCreate中
  fun initSDK(listener: OnlineStatusListener) {
    NIMClient.initSDK()
    NIMSDK.getAuthServiceObserve().observeOnlineStatus(
      {
        if(it?.wontAutoLogin() == true) {
          // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
          listener.onlineLoginOut()
        }
      }, true
    )
    NIMSDK.getMsgServiceObserve().observeBroadcastMessage({
      //网易云信支持全员广播消息，广播消息由服务端接口发起，对应用内的所有用户发送一条广播消息。客户端不支持发送， SDK 收到广播之后直接往上层通知，不支持客户端存储。
    }, true)
  }

  fun login(imId: String, imToken: String, callback: RequestCallback<LoginInfo>?) {
    val loginInfo = LoginInfo(imId, imToken)
    NIMSDK.getAuthService().login(loginInfo)
      .setCallback(callback)
  }

  fun isLogin(): Boolean {
    return NIMClient.getStatus() == StatusCode.LOGINED
  }

  fun logout() {
    NIMSDK.getAuthService().logout()
  }


}