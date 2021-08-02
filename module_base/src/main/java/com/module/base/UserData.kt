package com.module.base

/**
 *  Class:
 *  Other:返回参数：
 *  Create by jsji on  2021/7/5.
 */
data class UserData(val uid: String,
                    var hasFillBaseInfo: Boolean,
                    var token: String,
                    var refreshToken: String,
                    var imId: String,
                    var imToken: String) {

}
