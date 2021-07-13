package com.module.base.network

import com.module.base.BaseResponse

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/9.
 */
data class RefreshTokenData(val token: String, val expireTime: Long, val refreshExpireTime: Long,
                            val refreshToken: String)
