package com.module.base


/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/20.
 */
data class APPConfig(var bitrate: Int = 0) {
  //推流比特率

  override fun toString(): String {
    return "APPConfig(bitrate=$bitrate)"
  }

}