package com.library.share

import com.umeng.socialize.bean.SHARE_MEDIA

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/6.
 */
enum class SHARE_TYPE(val shareMedia: SHARE_MEDIA) {
  WEIXIN(SHARE_MEDIA.WEIXIN), WEIXIN_CIRCLE(SHARE_MEDIA.WEIXIN_CIRCLE),
  QQ(SHARE_MEDIA.QQ), SINA(SHARE_MEDIA.SINA),
}