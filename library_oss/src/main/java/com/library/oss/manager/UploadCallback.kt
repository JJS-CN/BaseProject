package com.library.oss.manager

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/14.
 */
interface UploadCallback {
  fun onUploadSuccess(urls: List<String>)

  fun onUploadFailed(error: String)
}