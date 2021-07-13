package com.module.user.net

import com.module.base.BaseResponse
import com.module.base.UserData
import com.module.base.network.SERVICE_NAME_USER
import retrofit2.http.*

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/7.
 */
interface UserService {
  //是否已注册 1：手机号是否注册 2：微信号是否注册
  @Headers(SERVICE_NAME_USER)
  @POST("/account/has_registered")
  @FormUrlEncoded
  suspend fun hasRegister(@Field("type") type: Int,
                          @Field("phoneNumber") phoneNumber: String): BaseResponse<Boolean>

  //密码需要（MD5加密）  register：注册 mp：手机号登录 captcha: 验证码登录
  @Headers(SERVICE_NAME_USER)
  @POST("/account/login")
  @FormUrlEncoded
  suspend fun login(@Field("type") type: String,
                    @Field("phoneNumber") phoneNumber: String,
                    @Field("pwd") pwd: String?,
                    @Field("captcha") captcha: String?): BaseResponse<UserData>

  @Headers(SERVICE_NAME_USER)
  @POST("/account/quit")
  suspend fun loginQuit(): BaseResponse<String>



  //获取登录/重置密码验证码 1 登录/注册 2重置密码
  @Headers(SERVICE_NAME_USER)
  @POST("/account/get_captcha")
  @FormUrlEncoded
  suspend fun getCaptcha(@Field("type") type: Int,
                         @Field("phoneNumber") phoneNumber: String): BaseResponse<String>

  //修改密码
  @Headers(SERVICE_NAME_USER)
  @POST("/account/update_password")
  @FormUrlEncoded
  suspend fun updatePassword(@Field("oldPwd") oldPwd: String,
                             @Field("newPwd") newPwd: String,
                             @Field("confirmPwd") confirmPwd: String): BaseResponse<String>

  //重置密码
  @Headers(SERVICE_NAME_USER)
  @POST("/account/reset_password")
  @FormUrlEncoded
  suspend fun resetPassword(@Field("phoneNumber") phoneNumber: String,
                            @Field("newPwd") newPwd: String,
                            @Field("confirmPwd") confirmPwd: String): BaseResponse<String>


}