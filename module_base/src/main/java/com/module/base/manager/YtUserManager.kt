package com.module.base.manager

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
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
  //保存用户手机号，用于用户token失效时时自动填充手机号，退出登录不附加！但应该跳转的时候携带过去比较好
  private val MMKV_KEY_USER_HISTORY_PHONE = "MMKV_KEY_USER_HISTORY_PHONE"
  private val mmkv_user by lazy { MMKV.mmkvWithID("User") }
  private var userData: UserData? = null
  fun getUserBody(): UserData? {
    //用户数据
    if(userData == null) {
      val user_data = mmkv_user.getString(MMKV_KEY_USER_BODY, "")
      if(!TextUtils.isEmpty(user_data)) {
        userData = Gson().fromJson(user_data, UserData::class.java)
      }
    }
    return userData
  }

  fun putUserBody(userData: UserData) {
    YtUserManager.userData = userData
    mmkv_user.putString(MMKV_KEY_USER_BODY, Gson().toJson(userData))
  }

  fun clearUser() {
    userData = null
    mmkv_user.clearAll()
  }

  fun isLogin(): Boolean {
    val isl = !getUserBody()?.uid.isNullOrEmpty()
    Log.e("YuntuUserManager", isl.toString())
    return isl
  }
}


/*
 fun MMKV.UserBody() {
  val mmkv_user = MMKV.mmkvWithID("User")
  val user_data = mmkv_user.getString("body", "")
  val userBody = Gson().fromJson<UserBody>(user_data, UserBody::class.java)
}*/
