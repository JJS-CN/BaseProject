package com.common.core.manager

import android.app.Application
import android.util.Log
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk


/**
 *  Class: 地址：https://x5.tencent.com/docs/questions.html
 *  Other:TBS内核首次使用和加载时，ART虚拟机会将Dex文件转为Oat，该过程由系统底层触发且耗时较长，很容易引起anr问题，解决方法是使用TBS的 ”dex2oat优化方案“。
 *  Create by jsji on  2021/7/12.
 */
object TBSManager {
  //只为webview服务，按理说只需要在使用时初始化就行
  fun init(app: Application) {
    // 在调用TBS初始化、创建WebView之前进行如下配置。也就是可以不在application初始化
    val map = HashMap<String, Any>()
    map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
    map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
    QbSdk.initTbsSettings(map)
    QbSdk.initX5Environment(app, object : QbSdk.PreInitCallback {
      override fun onCoreInitFinished() {
        Log.e("TBSManager", "onCoreInitFinished")
      }

      override fun onViewInitFinished(p0: Boolean) {
        Log.e("TBSManager", "onViewInitFinished:$p0")
      }

    })
  }
}