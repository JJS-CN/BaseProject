package com.module.base

import com.squareup.moshi.JsonClass


/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/20.
 */

@JsonClass(generateAdapter = true)
data class APPConfig(var bitrate: Int = 10) {
  //推流比特率

  override fun toString(): String {
    return "APPConfig(bitrate=$bitrate)"
  }

}