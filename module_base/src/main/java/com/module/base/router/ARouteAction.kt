package com.module.base.router

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter

/**
 *  Class:对外公布的路由跳转，各module内部跳转自行处理
 *  Other:带参的方法都新建方法暴露
 *  如果新增路由出问题InstantRun support error，debug运行需要卸载重装！！！！！！！！！！！！！！！！！！
 *  Create by jsji on  2021/6/30.
 */
object ARouteAction {
  //根据模块分组内容
  object APP {
    //实现模块共同path
    private const val path = "/app/"

    /*具体的跳转路由key*/
    //跳转首页
    const val PATH_HOME: String = path + "home"

    /*具体的执行方法*/
    fun goHome() {
      ARouter.getInstance().build(PATH_HOME)
        .navigation()
    }
  }

  object User {
    //跳转首页
    //实现模块共同path
    private const val path = "/user/"
    const val PATH_LOGIN: String = path + "login"
    const val KEY_LOGIN_PHONE = "KEY_LOGIN_PHONE"
    fun launchLoginActivity() {
      launchLoginActivity(null)
    }
    fun launchLoginActivity(phone:String?) {
      ARouter.getInstance().build(PATH_LOGIN)
        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        .navigation()
    }
  }
}