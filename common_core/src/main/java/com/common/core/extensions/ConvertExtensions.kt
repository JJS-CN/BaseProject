package com.common.core.extensions

import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.EncryptUtils

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/28.
 */

fun Float.dp2px(): Float {
  return ConvertUtils.dp2px(this).toFloat()
}

fun String.toMd5(): String {
  return EncryptUtils.encryptMD5ToString(this).lowercase()
}