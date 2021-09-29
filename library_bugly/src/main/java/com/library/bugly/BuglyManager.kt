package com.library.bugly

import android.app.Application
import com.tencent.bugly.Bugly

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/1.
 */
object BuglyManager {
    fun init(app: Application, appId: String, isTestChannel: Boolean) {
        //init方法会自动检测更新，所以尽量在进入到首页后再提示更新
        //第三个参数标识是否是debug模式，是的话数据不会上报到bugly，防止数据污染
        Bugly.init(app, appId, BuildConfig.DEBUG || isTestChannel);
    }
}