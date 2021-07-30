package com.read.bookclub.net

import com.common.core.network.viewmodel.IUIActionEvent
import com.module.base.BaseDataSource

/**
 *  Class: 继承业务层基础接口服务，单独实例化对应模块的apiservice，操作每个模块自己的内容
 *  Other:
 *  Create by jsji on  2021/7/5.
 */
class AppDataSource(iuiActionEvent: IUIActionEvent?) :
  BaseDataSource<AppService>(iuiActionEvent, AppService::class.java) {
}