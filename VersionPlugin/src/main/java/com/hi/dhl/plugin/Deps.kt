package com.hi.dhl.plugin

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/9/29.
 */
object Versions {
  val arouterVersion = "1.5.2"
  val ComponentVersion = "v1.9.0-androidx-java8-Stable2"
  val lifecycleVersion = "2.3.1"
  val coilVersion = "1.2.2"
  val roomVersion = "2.3.0"
  val navigationVersion = "2.4.0-alpha04"
  val composeVersion = "1.0.1"
}

object Deps {
  val multidex = "androidx.multidex:multidex:2.0.1"
  val appcompat = "androidx.appcompat:appcompat:1.3.0"

  val material = "com.google.android.material:material:1.2.1"
  val cardview = "androidx.cardview:cardview:1.0.0"
  val annotation = "androidx.annotation:annotation:1.2.0"
  val recyclerview = "androidx.recyclerview:recyclerview:1.2.0"
  val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
  val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
  val coordinatorlayout = "androidx.coordinatorlayout:coordinatorlayout:1.1.0"
  val navigation_fragment_ktx =
    "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
  val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

  /**功能库 **/
  //分页
  val paging = "androidx.paging:paging-runtime-ktx:3.0.0"

  //本地数据
  val room_runtime = "androidx.room:room-runtime:${Versions.roomVersion}"
  val room_ktx = "androidx.room:room-ktx:${Versions.roomVersion}"
  val room_compiler = "androidx.room:room-compiler:${Versions.roomVersion}"

  //kotlin支持
  val core_ktx = "androidx.core:core-ktx:1.6.0"
  val kotlinx_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3"
  val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"
  val lifecycle_viewmodel_ktx =
    "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
  val lifecycleL_livedata_ktx =
    "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"


  //第三方框架
  val permissionx = "com.permissionx.guolindev:permissionx:1.4.0"
  val BRVAH = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.6"

  //向xml中附加shape自定义标签属性 https://github.com/JavaNoober/BackgroundLibrary/wiki
  val BackgroundLibrary = "com.github.JavaNoober.BackgroundLibrary:libraryx:1.7.2"

  //MMKV基于mmap的高性能kv存储 https://github.com/Tencent/MMKV
  val mmkv = "com.tencent:mmkv-static:1.2.10"

  //沉浸式状态栏 https://github.com/Zackratos/UltimateBarX
  val ultimatebarx = "com.zackratos.ultimatebarx:ultimatebarx:0.6.0"

  //唯一设备标识码OAID获取 https://github.com/gzu-liyujiang/Android_CN_OAID
  val Android_CN_OAID = "com.github.gzu-liyujiang:Android_CN_OAID:4.2.1"

  //基础库+自带混淆 https://github.com/Blankj/AndroidUtilCode
  val utilcodex = "com.blankj:utilcodex:1.30.6"

  //UI界面适配+自带混淆 https://github.com/JessYanCoding/AndroidAutoSize
  val autosize = "com.github.JessYanCoding:AndroidAutoSize:v1.2.1"

  //事件总线 https://github.com/JeremyLiao/LiveEventBus
  val liveDataBus = "io.github.jeremyliao:live-event-bus-x:1.8.0"

  //图片请求库 https://coil-kt.github.io/coil/
  val coil = "io.coil-kt:coil:${Versions.coilVersion}"
  val coilGif = "io.coil-kt:coil-gif:${Versions.coilVersion}"
  val coilSvg = "io.coil-kt:coil-svg:${Versions.coilVersion}"
  val coilVideo = "io.coil-kt:coil-video:${Versions.coilVersion}"

  //图片压缩库 https://github.com/zetbaitsu/Compressor
  val compressor = "id.zelory:compressor:3.0.1"

  //图片裁剪库 https://github.com/Yalantis/uCrop
  val ucrop = "com.github.yalantis:ucrop:2.2.6"

  //图片选择库 https://github.com/LuckSiege/PictureSelector/ 此框架包含photoView和Luban和uCrop
  val pictureselector = "io.github.lucksiege:pictureselector:v2.7.3-rc06"

  //加载中占位骨架
  val broccoli = "me.samlss:broccoli:1.0.0"

  //dialog需要5.0，并且正在开发中，不是很稳定 https://github.com/kongzue/DialogX
  val DialogX = "com.github.kongzue.DialogX:DialogX:0.0.39"

  //basePopup  https://github.com/razerdp/BasePopup
  val BasePopup = "io.github.razerdp:BasePopup:3.1.0"


  //组件化路由
  //Component https://github.com/xiaojinzi123/Component/wiki
  val Component =
    "com.github.xiaojinzi123.Component:component-impl-ktx:${Versions.ComponentVersion}"
  val Component_compiler =
    "com.github.xiaojinzi123.Component:component-compiler:${Versions.ComponentVersion}"
  val Component_plugin =
    "com.github.xiaojinzi123.Component:component-plugin:${Versions.ComponentVersion}"

  //如果新增路由出问题InstantRun support error，debug运行需要卸载重装！！！！！！！！！！！！！！！！！！
  /*   "arouter-api"               ="com.alibaba:arouter-api:${Versions.arouterVersion}"
     "arouter-compiler"          ="com.alibaba:arouter-compiler:${Versions.arouterVersion}"*/
  //网络请求
  val retrofit2 = "com.squareup.retrofit2:retrofit:2.9.0"
  val converter_gson = "com.squareup.retrofit2:converter-gson:2.9.0"
  val converter_moshi = "com.squareup.retrofit2:converter-moshi:2.9.0"
  val moshi_kotlin_codegen = "com.squareup.moshi:moshi-kotlin-codegen:1.12.0"
  val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:1.11.0"

  val converter_serialization =
    "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
  val okhttp_bom = "com.squareup.okhttp3:okhttp-bom:4.9.1"

  //webview
  val tbssdk = "com.tencent.tbs.tbssdk:sdk:43939"

  //compose
  val compose_ui = "androidx.compose.ui:ui:${Versions.composeVersion}"
  val compose_material = "androidx.compose.material:material:${Versions.composeVersion}"
  val compose_ui_tooling_preview =
    "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}"
  val compose_activity = "androidx.activity:activity-compose:1.3.1"

  // 使用Coil加载图片时导入
  val accompanist_coil = "com.google.accompanist:accompanist-coil:0.15.0"
  // 使用Glide加载图片时导入
  //implementation("com.google.accompanist:accompanist-glide:0.14.0")

  // UI 测试
  val compose_ui_test_junit4 = "androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}"
  val compose_ui_tooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"

}