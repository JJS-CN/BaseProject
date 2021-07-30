package com.library.netease

import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.Observer
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.chatroom.ChatRoomService
import com.netease.nimlib.sdk.chatroom.ChatRoomServiceObserver
import com.netease.nimlib.sdk.chatroom.model.ChatRoomKickOutEvent
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData
import com.netease.nimlib.sdk.msg.MsgServiceObserve
import com.netease.nimlib.sdk.msg.model.IMMessage

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/16.
 */
object ChatRoomManager {
  fun enterChatRoom(roomId: String, callback: RequestCallback<EnterChatRoomResultData>) {
    val roomData = EnterChatRoomData(roomId)
    NIMClient.getService(ChatRoomService::class.java)
      .enterChatRoomEx(roomData, 10)
      .setCallback(callback)
  }

  fun observeChatRoomReceiveMessage(callback: Observer<List<ChatRoomMessage>>, register: Boolean) {
    //需要反注册，所以在加入房间时注册，退出房间时关闭；同时传入消息处理类，执行消息处理方法
    NIMClient.getService(ChatRoomServiceObserver::class.java)
      .observeReceiveMessage(callback, register)
  }

  fun observeChatSingleReceiveMessage(callback: Observer<List<IMMessage>>, register: Boolean) {
    NIMClient.getService(MsgServiceObserve::class.java)
      .observeReceiveMessage(callback, register)
  }

  fun observeKickOutEvent(callback: Observer<ChatRoomKickOutEvent>, register: Boolean) {
    NIMClient.getService(ChatRoomServiceObserver::class.java)
      .observeKickOutEvent(callback, register)
  }

  fun exitChatRoom(roomId: String) {
    NIMClient.getService(ChatRoomService::class.java)
      .exitChatRoom(roomId)
  }
}