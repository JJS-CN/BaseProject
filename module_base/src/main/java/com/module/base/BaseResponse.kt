package com.module.base

import com.common.core.network.bean.IHttpWrapBean

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/1.
 */
data class BaseResponse<T>(val infocode: Int = 0,
                           val info: String = "",
                           val status: Int = 0,
                           val data: T?) :
  IHttpWrapBean<T> {
  override val httpCode: Int
    get() = infocode

  override val httpMsg: String
    get() = info

  override val httpData: T?
    get() = data

  //网络请求是否成功
  override val httpIsSuccess: Boolean
    get() = infocode == 0

}