package com.common.core.manager

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.wifi.WifiManager
import android.text.TextUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.Utils
import com.github.gzuliyujiang.oaid.DeviceID
import com.github.gzuliyujiang.oaid.IGetter

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/2.
 */
object UUIDManager {
  private var DEVICE_OAID: String? = null
  private var UUID_MD5: String? = null

  fun initRomOAID(app: Application) {
    DeviceID.getOAID(app, object : IGetter {
      override fun onOAIDGetComplete(result: String) {
        // 不同厂商的OAID/AAID格式是不一样的，可进行MD5、SHA1之类的哈希运算统一
        DEVICE_OAID = result
      }

      override fun onOAIDGetError(error: Exception) {
        // 获取OAID/AAID失败
      }
    })
  }

  @SuppressLint("HardwareIds")
  fun getUUID(): String {
    if(TextUtils.isEmpty(UUID_MD5)) {
      //优先使用oaid
      var device_id = DEVICE_OAID
      if(TextUtils.isEmpty(device_id)) {
        //没有时使用androidid填充
        device_id = DeviceUtils.getAndroidID()
      }
      val wifi =
        Utils.getApp().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
      val mac = wifi.connectionInfo.macAddress
      val builder = StringBuilder()
      builder.append(mac).append(":")

      if(TextUtils.isEmpty(device_id)) {
        device_id = mac
      }

      builder.append(device_id)
      UUID_MD5 = CryptoUtils.MD5(builder.toString())
    }
    return UUID_MD5 ?: ""
  }
}