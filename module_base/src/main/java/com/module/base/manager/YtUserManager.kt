package com.module.base.manager

import android.text.TextUtils
import android.util.Log
import com.library.netease.NeteaseManager
import com.library.zego.ZegoManager
import com.module.base.UserData
import com.tencent.mmkv.MMKV

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/5.
 */
object YtUserManager {
  //保存用户信息
  private val MMKV_KEY_USER_BODY = "MMKV_KEY_USER_BODY"
  private lateinit var mmkv_user: MMKV
  /*public lateinit var userData: UserData

  init {
    mmkv_user = MMKV.mmkvWithID("User")
    val data = mmkv_user.getString(MMKV_KEY_USER_BODY, "{}")
    userData = Gson().fromJson(data, UserData::class.java)
  }


  //保存用户手机号，用于用户token失效时时自动填充手机号，退出登录不附加！但应该跳转的时候携带过去比较好
  private val MMKV_KEY_USER_HISTORY_PHONE = "MMKV_KEY_USER_HISTORY_PHONE"

  fun getUserBody(): UserData {
    //用户数据
    return userData
  }

  fun putUserBody(userData: UserData) {
    this.userData = userData
    mmkv_user.putString(MMKV_KEY_USER_BODY, Gson().toJson(userData))
  }

  fun clearUser() {
    mmkv_user.clearAll()
    //填充空数据
    userData = Gson().fromJson("{}", UserData::class.java)
    //重置第三方
    ZegoManager.destroyEngine()
    NeteaseManager.logout()
    // TODO: 2021/7/29 重置推送

  }

  fun isLogin(): Boolean {
    return !getUserBody().uid.isNullOrEmpty()
  }*/
}


/*
 fun MMKV.UserBody() {
  val mmkv_user = MMKV.mmkvWithID("User")
  val user_data = mmkv_user.getString("body", "")
  val userBody = Gson().fromJson<UserBody>(user_data, UserBody::class.java)
}*/
