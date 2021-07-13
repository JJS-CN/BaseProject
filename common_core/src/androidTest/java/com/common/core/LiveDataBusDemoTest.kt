package com.common.core

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/2.
 */
class LiveDataBusDemoTest {
  /*  订阅消息
    以生命周期感知模式订阅消息
    LiveEventBus
    .get("some_key", String.class)
    .observe(this, new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
        }
    });
    以Forever模式订阅消息
    LiveEventBus
    .get("some_key", String.class)
    .observeForever(observer);
    发送消息
    不定义消息直接发送
    LiveEventBus
    .get("some_key")
    .post(some_value);
    先定义消息，再发送消息
    public class DemoEvent implements LiveEvent {
        public final String content;

        public DemoEvent(String content) {
            this.content = content;
        }
    }
    LiveEventBus
    .get(DemoEvent.class)
    .post(new DemoEvent("Hello world"));*/
}