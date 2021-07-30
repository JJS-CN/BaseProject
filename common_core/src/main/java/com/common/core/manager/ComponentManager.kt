package com.common.core.manager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.common.core.BuildConfig
import com.common.core.router.ComponentRouterInjectable
import com.xiaojinzi.component.Component
import com.xiaojinzi.component.Config

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/6/30.
 */
object ComponentManager {
  fun init(app: Application) {
    //初始化路由
    // 初始化
    Component.init(
      BuildConfig.DEBUG,
      Config.with(app)
        // 这里表示使用 ASM 字节码技术加载模块, 默认是 false
        // 如果是 true 请务必配套使用 Gradle 插件, 下一步就是可选的配置 Gradle 插件
        // 如果是 false 请直接略过下一步 Gradle 的配置
        .optimizeInit(true)
        // 自动加载所有模块, 打开此开关后下面无需手动注册了
        // 但是这个依赖 optimizeInit(true) 才会生效
        .autoRegisterModule(true) // 1.7.9+
        .build()
    );
    app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
      override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        if(activity is ComponentRouterInjectable) {
          ///注入参数
          Component.inject(activity)
        }
      }

      override fun onActivityStarted(p0: Activity) {
      }

      override fun onActivityResumed(p0: Activity) {
      }

      override fun onActivityPaused(p0: Activity) {
      }

      override fun onActivityStopped(p0: Activity) {
      }

      override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
      }

      override fun onActivityDestroyed(p0: Activity) {
      }

    })
  }
}