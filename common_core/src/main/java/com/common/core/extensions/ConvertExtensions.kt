package com.common.core.extensions

import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.EncryptUtils

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/28.
 */

/**
 * 将dp单位转换为px单位
 */
fun Float.dp2px(): Float {
  return ConvertUtils.dp2px(this).toFloat()
}

/**
 * 将字符串进行MD5加密：小写
 */
fun String.toMd5lowercase(): String {
  return EncryptUtils.encryptMD5ToString(this).lowercase()
}