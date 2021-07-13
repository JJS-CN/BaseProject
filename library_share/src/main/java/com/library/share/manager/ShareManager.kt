package com.library.share.manager

import android.app.Activity
import com.library.share.SHARE_TYPE
import com.umeng.socialize.ShareAction
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMMin
import com.umeng.socialize.media.UMWeb

/**
 *  Class: 防止api改变导致大部分代码失效，所以第三方库最好通过包装后再使用
 *  Other:
 *  Create by jsji on  2021/7/6.
 */
class ShareManager(private val activity: Activity) {
  private val shareAction = ShareAction(activity)

  fun withUMWeb(url: String,
                title: String,
                desc: String,
                imageUrl: String): ShareManager {
    val web = UMWeb(url)
    web.title = title
    web.description = desc
    web.setThumb(UMImage(activity, imageUrl))
    shareAction.withMedia(web)
    return this
  }

  fun withUMMin(url: String,
                title: String,
                desc: String,
                path: String,
                userName: String,
                image: UMImage): ShareManager {
    //兼容低版本的网页链接
    val umMin = UMMin(url)
    umMin.title = title
    umMin.description = desc
    //小程序页面路径
    umMin.path = path
    // 小程序原始id,在微信平台查询
    umMin.userName = userName
    // 小程序消息封面图片
    umMin.setThumb(image)
    shareAction.withMedia(umMin)
    return this
  }

  fun setPlatform(type: SHARE_TYPE): ShareManager {
    shareAction.platform = type.shareMedia
    return this
  }

  fun share() {
    shareAction.share()
  }
}