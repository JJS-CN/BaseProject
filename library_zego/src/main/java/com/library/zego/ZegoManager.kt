package com.library.zego

import android.app.Application
import im.zego.zegoexpress.ZegoExpressEngine
import im.zego.zegoexpress.callback.IZegoEventHandler
import im.zego.zegoexpress.constants.ZegoScenario
import im.zego.zegoexpress.entity.ZegoAudioConfig
import im.zego.zegoexpress.entity.ZegoRoomConfig
import im.zego.zegoexpress.entity.ZegoUser

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/13.
 */
class ZegoManager {

  companion object {
    var isStartPublishSteam = false//是否正在推流（上麦）
    val isTestEnv = true
    fun create(app: Application?, listener: IZegoEventHandler) {
      create(app, 48000, listener)
    }

    fun create(app: Application?, bitrate: Int, listener: IZegoEventHandler) {
      //可以在房间打开时初始化
      val engine = ZegoExpressEngine.createEngine(
        121484752,
        "42e639cb480e764b6e220a2fc90b90062ea803d3b41e0901186ea283f6db2021",
        isTestEnv,
        ZegoScenario.GENERAL,
        app,
        listener
      )
      val config = ZegoAudioConfig()
      config.bitrate = bitrate
      engine.audioConfig = config
      isStartPublishSteam = false
    }

    fun loginRoom(userId: String, roomId: String) {
      // “isUserStatusNotify” 参数取值为 “true” 时，用户才能收到 onRoomUserUpdate 回调。
      val config = ZegoRoomConfig()
      config.isUserStatusNotify = true

      val user = ZegoUser(userId)
      ZegoExpressEngine.getEngine().loginRoom(roomId, user, config)
    }

    fun logoutRoom(roomId: String) {
      isStartPublishSteam = false
      ZegoExpressEngine.getEngine().logoutRoom(roomId)
      //同时释放即构
      destroyEngine()
    }

    fun startPublishingStream(streamID: String) {
      isStartPublishSteam = true
      ZegoExpressEngine.getEngine().startPublishingStream(streamID)
    }

    fun startPlayingStream(streamID: String) {
      ZegoExpressEngine.getEngine().startPlayingStream(streamID)
    }

    fun stopPublishingStream() {
      isStartPublishSteam = false
      ZegoExpressEngine.getEngine().stopPublishingStream()
    }

    fun stopPlayingStream(streamID: String) {
      ZegoExpressEngine.getEngine().stopPlayingStream(streamID)
    }

    fun muteMicrophone(closeAudio: Boolean) {
      //是否静音（关闭麦克风）；[true] 表示静音（关闭麦克风）；[false] 表示开启麦克风。
      ZegoExpressEngine.getEngine().muteMicrophone(closeAudio)
    }

    fun isMicrophoneMuted(): Boolean {
      //检查麦克风是否设置为静音
      return ZegoExpressEngine.getEngine().isMicrophoneMuted
    }

    /* fun mutePublishStreamAudio(closeAudio: Boolean) {
       //是否停止发送音频流；true 表示不发送音频流；false 表示发送音频流；默认为 false
       ZegoExpressEngine.getEngine().mutePublishStreamAudio(closeAudio)
     }*/
    fun destroyEngine() {
      ZegoExpressEngine.destroyEngine(null)
    }

  }

}