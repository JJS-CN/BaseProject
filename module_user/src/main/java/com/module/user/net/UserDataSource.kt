package com.module.user.net

import com.common.core.network.viewmodel.IUIActionEvent
import com.module.base.BaseDataSource

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/7.
 */
class UserDataSource(iuiActionEvent: IUIActionEvent?) :
  BaseDataSource<UserService>(iuiActionEvent, UserService::class.java) {
}