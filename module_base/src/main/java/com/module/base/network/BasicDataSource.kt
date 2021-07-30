package com.module.base.network

import com.blankj.utilcode.util.ApiUtils
import com.common.core.network.viewmodel.IUIActionEvent
import com.module.base.BaseDataSource

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/28.
 */
class BasicDataSource(iActionEvent: IUIActionEvent?) : BaseDataSource<BasicService>(
  iActionEvent,
  BasicService::class.java) {
}