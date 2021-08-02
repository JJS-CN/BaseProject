package com.read.bookclub.net

import com.module.base.APPConfig
import com.module.base.BaseResponse
import com.module.base.network.SERVICE_NAME_USER
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/20.
 */
interface AppService {


  @Headers(SERVICE_NAME_USER)
  @POST("/zego/audio_conf")
  suspend fun getAudioConf(): BaseResponse<Any?>
}