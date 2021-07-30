package com.module.base.message.data

/**
 *  Class:
 *  Other:
 *  @type 消息类型
0：普通类型，需要渲染；
1：系统类型，状态变更，无需渲染。
 * @time 下发时间，10位/秒  超时丢弃、同类过旧丢弃
 *  Create by jsji on  2021/7/19.
 */
open class BaseMessageAction() {
  val type: Int = 0
  val subType: Int = 0
  val action: Int = 0
  val time: Long = 0
  fun isUIType(): Boolean {
    return type == 0
  }

  fun isSystemType(): Boolean {
    return type == 1
  }

  override fun toString(): String {
    return "BaseMessageAction(type=$type, subType=$subType, action=$action, time=$time)"
  }


}