package com.module.base.network

import com.blankj.utilcode.util.EncryptUtils

/**
 *  Class: 接口签名校验
 *  Other: 先通过getCurrentTime获取时间保存，到时候传入到nwtime中。再通过getTimeSign获取签名
 *  Create by jsji on  2021/7/5.
 */
object SignUtil {
  private var serviceTimeOffset = 0 //线上时间与本地时间的偏差值---单位秒

  private val TAG_SIGN_USER = "social_user"

  fun getCurrentTime(): Int {
    //获取当前时间，根据偏差值计算得到
    val phoneTime = System.currentTimeMillis() / 1000
    return (phoneTime - serviceTimeOffset).toInt()
  }

  fun getTimeSign(time: Int): String {
    return getTimeSign(TAG_SIGN_USER, time)
  }

  fun getTimeSign(tag: String, time: Int): String {
    var md5Url = ""
    try {
      //获取fungolive + time 的加密字符串
      md5Url = EncryptUtils.encryptMD5ToString(tag + time).lowercase()
    } catch(e: Exception) {
      e.printStackTrace()
    }
    return md5Url
  }

}