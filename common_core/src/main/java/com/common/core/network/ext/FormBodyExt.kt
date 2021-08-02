package com.common.core.network.ext

import okhttp3.FormBody

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/8/1.
 */
fun FormBody.toParamsMap(): Map<String, Any> {
  val map = HashMap<String, Any>()
  for(i in 0 until this.size) {
    val key = this.name(i)
    val value = this.value(i)
    //加入之前，判断是否已有相同的key内容。
    //因为retrofit会将arraylist类型数据转换成多个相同的key进行返回
    val cache = map[key]
    if(cache == null) {
      map[key] = value
    } else {
      if(cache is ArrayList<*>) {
        //如果cache已经是arraylist，直接附加
        cache as ArrayList<Any>
        cache.add(value)
      } else {
        //如果不是，将内容转换成arraylist
        val list = ArrayList<Any>()
        list.add(cache)
        list.add(value)
        map[key] = list
      }
    }
  }
  return map
}

