package com.module.base

import com.common.core.network.bean.IHttpWrapBean

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/1.
 */
data class BaseResponse<T>(val code: Int, val msg: String, val data: T) :
    IHttpWrapBean<T> {
    override val httpCode: Int
        get() = code

    override val httpMsg: String
        get() = msg

    override val httpData: T
        get() = data

    //网络请求是否成功
    override val httpIsSuccess: Boolean
        get() = code == 0

}