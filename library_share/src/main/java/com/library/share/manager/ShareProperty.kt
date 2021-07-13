package com.module.share

import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMMin
import com.umeng.socialize.media.UMWeb


/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/6.
 */

fun ShareAction.createUMWeb(url: String,
                            title: String,
                            desc: String,
                            image: UMImage): ShareAction {
  val web = UMWeb(url)
  web.title = title
  web.description = desc
  web.setThumb(image)
  this.withMedia(web)
  return this
}


fun ShareAction.createUMMin(url: String,
                            title: String,
                            desc: String,
                            path: String,
                            userName: String,
                            image: UMImage): ShareAction {
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
  this.withMedia(umMin)
  return this
}




