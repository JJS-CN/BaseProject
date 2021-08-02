package com.common.core.constants

/**
 *  Class: 基础scheme的AuthorityKey列表
 *  Other: 业务模块需要时再定义自己的Constants类
 *  Create by jsji on  2021/7/12.
 */
enum class BaseSchemeActions(val value: String) {
  openUrlInApp("openUrlInApp"), //应用内H5
  openUrlInBrowser("openUrlInBrowser"),//应用外H5
}