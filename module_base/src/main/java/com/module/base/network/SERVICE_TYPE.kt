package com.module.base.network

import com.common.core.network.base.CallFactoryProxy
import com.module.base.BaseDataSource

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/7.
 */
enum class SERVICE_TYPE(var baseUrl: String, val signKey: String) {
  USER("http://api.test.chaojizb.com/", "social_user")

}

public const val SERVICE_NAME_USER = CallFactoryProxy.NAME_BASE_URL_KEY + ":" + "USER"