package com.library.share

import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.bean.SHARE_MEDIA

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/6.
 */
interface ThirdLoginAuthListener {

  fun onStart(var1: SHARE_TYPE?)

  fun onComplete(var1: SHARE_TYPE?, var2: Int, userEntry: AuthUserEntry)

  fun onError(var1: SHARE_TYPE?, var2: Int, var3: Throwable?)

  fun onCancel(var1: SHARE_TYPE?, var2: Int)


}