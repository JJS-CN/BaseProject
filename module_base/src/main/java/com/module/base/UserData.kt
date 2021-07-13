package com.module.base

/**
 *  Class:
 *  Other:返回参数：
{
"code": 0,
"msg": "",
"data": {
"hasFillBaseInfo": false,  // 是否已补全用户信息
"token": "7b9960970af94df5bab893a947dc3a9f", // 用户accessToken
"uid": 1000000000, // 用户ID
"expireTime": 1625671027, // accessToken 过期时间
"refreshExpireTime": 2592000000, // refreshToken 过期时间
"refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaXNzIjoiYWRtaW4iLCJpYXQiOjE2MjU2NTMwMjgsImV4cCI6MTYyODI0NTAyOH0.7EAZDqI2EeQeyhXKovdPNDHdVttuDYTu8HcmtAerTyQ"  // 用户refreshToken
},
"success": true
}
 *  Create by jsji on  2021/7/5.
 */
data class UserData(val uid: String,
                    val hasFillBaseInfo: Boolean,
                    var token: String,
                    var expireTime: Long,
                    var refreshExpireTime: Long,
                    var refreshToken: String) {
  //当前时间是否超出refresh有效时间
  fun isOutSideRefreshTime(): Boolean {
    return System.currentTimeMillis() / 1000 > refreshExpireTime
  }

  //当前时间是否超出refresh有效时间
  fun isOutSideTokenTime(): Boolean {
    return System.currentTimeMillis() / 1000 > expireTime
  }

}
