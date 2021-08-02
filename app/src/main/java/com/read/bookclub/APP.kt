package com.read.bookclub

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.Utils
import com.common.core.manager.CoilManager
import com.common.core.manager.ComponentManager
import com.common.core.ui.BaseActivity
import com.common.core.ui.BaseApplication
import com.library.netease.ChatRoomManager
import com.library.netease.NeteaseManager
import com.library.zego.ZegoManager
import com.module.base.manager.YtUserManager
import com.netease.nimlib.sdk.Observer
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage
import com.noober.background.BLAutoInjectController
import com.xiaojinzi.component.Component
import com.xiaojinzi.component.Config
import com.xiaojinzi.component.impl.application.ModuleManager
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/2.
 */
class APP : BaseApplication() {
  override fun onCreate() {
    super.onCreate()
    ComponentManager.init(this)
    CoilManager.init(this)
   /* NeteaseManager.config(
      this,
      "xxx",
      YtUserManager.userData.imId,
      YtUserManager.userData.imToken
    )*/
    ActivityUtils.addActivityLifecycleCallbacks(object : Utils.ActivityLifecycleCallbacks() {
      override fun onActivityStarted(activity: Activity) {
        super.onActivityStarted(activity)
        if(activity is BaseActivity<*>) {
          //在create中调用无效,所以在此设置
          UltimateBarX.with(activity)
            .color(Color.WHITE)
            .fitWindow(true)
            .light(true)
            .applyStatusBar()
        }
      }
    })
  }
}