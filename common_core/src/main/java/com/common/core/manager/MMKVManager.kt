package com.common.core.manager

import android.app.Application
import com.tencent.mmkv.MMKV

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/5.
 */
object MMKVManager {
  fun init(app: Application) {
    MMKV.initialize(app)
  }
}