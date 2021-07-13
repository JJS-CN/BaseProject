package com.module.base.network

import com.module.base.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/9.
 */
interface TokenService {
  @Headers(SERVICE_NAME_USER)
  @POST("/account/refresh_token")
  @FormUrlEncoded
  suspend fun refreshToken(@Field("refreshToken") refreshToken: String?): BaseResponse<RefreshTokenData>
}