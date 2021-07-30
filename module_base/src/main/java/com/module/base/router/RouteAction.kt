package com.module.base.router

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import com.blankj.utilcode.util.ActivityUtils
import com.xiaojinzi.component.bean.ActivityResult
import com.xiaojinzi.component.impl.*
import java.math.BigInteger

/**
 *  Class:对外公布的路由跳转，各module内部跳转自行处理
 *  Other:带参的方法都新建方法暴露
 *  如果新增路由出问题InstantRun support error，debug运行需要卸载重装！！！！！！！！！！！！！！！！！！
 *  Create by jsji on  2021/6/30.
 */
@SuppressLint("CheckResult")
object RouteAction {
  /*object Consts {
    const val FLAG_NEED_IM_LOGIN = 1.shl(0)
    const val FLAG_NEED_USER_LOGIN = 1.shl(1)


    fun hasFlag(extra: Int, flag: Int): Boolean {
      return extra.and(flag) == flag
    }

    private fun getFullBinaryString(num: Int): kotlin.String? {
      val s = Integer.toBinaryString(num)
      return String.format("%032d", BigInteger(s))
    }

    private fun getbitForPoint(x: Int, k: Int): Int {
      //取右侧第k位值，下标从0开始
      //解释：原值右移k-1位后，与1进行 与运算
      return x.shr(k - 1).and(1)
    }

    private fun putbitForPoint(x: Int, k: Int): Int {
      //第几位赋值1，下标从0开始，
      //解释：1左移k-1位后，与原值进行 或运算
      return x.or(1.shl(k - 1))
    }
  }*/

  object Interceptor {
    const val xxxx = ""
  }

  //根据模块分组内容
  object APP {
    //实现模块共同path
    private const val path = "app/"

    /*具体的跳转路由key*/
    //跳转首页
    const val PATH_HOME: String = path + "home"

    /*具体的执行方法*/
    fun goHome() {
      Router.with().hostAndPath(PATH_HOME)
        .addIntentFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        .forward()
    }
  }

  object User {
    //跳转首页
    //实现模块共同path
    private const val path = "user/"
    const val PATH_REGISTER: String = path + "register"
    const val PATH_LOGIN: String = path + "login"
    const val PATH_SPLASH: String = path + "splash"
    const val KEY_LOGIN_PHONE = "KEY_LOGIN_PHONE"

    fun launchReLoginActivity() {
      Router.with().hostAndPath(PATH_SPLASH)
        .addIntentFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        .forward()
    }

    fun launchLoginActivity() {
      launchLoginActivity(null)
    }

    fun launchLoginActivity(phone: String?) {
      Router.with().hostAndPath(PATH_LOGIN)
        .forward()
    }

    fun launchRegisterActivity() {
      Router.with().hostAndPath(PATH_REGISTER)
        .forward()
    }
  }

  object Room {
    private const val path = "room/"
    const val PATH_CREATE: String = path + "create"
    const val PATH_INFO: String = path + "info"

    fun launchRoomCreateActivity() {
      Router.with().hostAndPath(PATH_CREATE)
        .forward()
    }

    fun launchRoomInfoActivity(roomId: Int) {
      Router.with().hostAndPath(PATH_INFO)
        .putInt("roomId", roomId)
        .forward()
    }

    fun launchRoomInfoActivity(activity: Activity,
                               roomId: Int,
                               requestCode: Int,
                               block: ((result: ActivityResult) -> Unit)? = null) {
      Router.with(activity)
        .hostAndPath(PATH_INFO)
        .putInt("roomId", roomId)
        .requestCode(requestCode)
        .forwardForResult(object : BiCallback<ActivityResult> {
          override fun onCancel(originalRequest: RouterRequest?) {

          }

          override fun onError(errorResult: RouterErrorResult) {
          }

          override fun onSuccess(result: RouterResult, t: ActivityResult) {
            block?.invoke(t)
          }
        })
    }
  }
}