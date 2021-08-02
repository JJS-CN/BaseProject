package com.common.core.network.exception

import java.lang.Exception

/**
 *  Class: 用来定义服务器返回的错误
 *  Other:
 *  @param errorCode 服务器返回的错误码
 *  @param errorMessage 服务器返回的错误信息，默认应该toast用户
 *  Create by jsji on  2021/7/31.
 */
class ApiException(val errorCode: Int = 0, val errorMessage: String = "") :
  Exception(errorMessage) {
}