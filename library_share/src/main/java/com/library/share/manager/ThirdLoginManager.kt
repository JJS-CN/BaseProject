package com.library.share.manager

import android.app.Activity
import android.content.Intent
import com.library.share.AuthUserEntry
import com.library.share.SHARE_TYPE
import com.library.share.ThirdLoginAuthListener
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA

/**
 *  Class: 第三方登录,
 *  Other:在activity的onActivityResult实现对应方法。再通过getPlatformInfo开始第三方登录
 *  Create by jsji on  2021/7/6.
 */
object ThirdLoginManager {
  fun getPlatformInfo(activity: Activity,
                      shareType: SHARE_TYPE,
                      thirdLoginAuthListener: ThirdLoginAuthListener) {
    UMShareAPI.get(activity)
      .getPlatformInfo(activity, shareType.shareMedia, object : UMAuthListener {
        override fun onStart(p0: SHARE_MEDIA?) {
          thirdLoginAuthListener.onStart(SHARE_TYPE.valueOf(p0?.name.toString()))
        }

        override fun onComplete(p0: SHARE_MEDIA?, p1: Int, data: MutableMap<String, String>?) {
          val userEntry =
            AuthUserEntry(data?.get("uid"), data?.get("uid"), data?.get("uid"), data?.get("uid"))
          thirdLoginAuthListener.onComplete(SHARE_TYPE.valueOf(p0?.name.toString()), p1, userEntry)
        }

        override fun onError(p0: SHARE_MEDIA?, p1: Int, p2: Throwable?) {
          thirdLoginAuthListener.onError(SHARE_TYPE.valueOf(p0?.name.toString()), p1, p2)
        }

        override fun onCancel(p0: SHARE_MEDIA?, p1: Int) {
          thirdLoginAuthListener.onCancel(SHARE_TYPE.valueOf(p0?.name.toString()), p1)
        }

      })
  }

  fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, intent: Intent?) {

    UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, intent)
  }
}