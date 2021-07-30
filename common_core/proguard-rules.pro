
#viewbinding 因封装使用了反射，所以混淆规则需将反射调用方法禁止混淆
-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
  public static ** inflate(...); public static ** bind(***);
  }
#LiveDataBus
-dontwarn com.jeremyliao.liveeventbus.**
-keep class com.jeremyliao.liveeventbus.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.arch.core.** { *; }

#okhttp3
-dontwarn okhttp3.internal.platform.ConscryptPlatform
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

#retrofit2
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn sun.misc.**
-dontwarn sorg.codehaus.mojo.animal_sniffer.**
-dontwarn org.codehaus.**
-dontwarn java.nio.**
-dontwarn java.lang.invoke.**

#Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

#ultimatebarx
-keep class com.zackratos.ultimatebarx.ultimatebarx.** { *; }
-keep public class * extends androidx.fragment.app.Fragment { *; }

#ucrop
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#dialog

#basePopup
-dontwarn razerdp.basepopup.**
-keep class razerdp.basepopup.**{*;}

#x5-web
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**
-keep class com.tencent.smtt.** {
    *;
}
-keep class com.tencent.tbs.** {
    *;
}
#PictureSelector 2.0
-keep class com.luck.picture.lib.** { *; }
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# 小金子组件化框架 不要警告
-dontwarn com.xiaojinzi.component.**
# 所有本包下面的类和接口都不混淆
-keep class com.xiaojinzi.component.** {*;}
-keep interface com.xiaojinzi.component.** {*;}
#这些是让被标记的类不被混淆
-keep @com.xiaojinzi.component.anno.InterceptorAnno class * {*;}
-keep @com.xiaojinzi.component.anno.GlobalInterceptorAnno class * {*;}
-keep @com.xiaojinzi.component.anno.ConditionalAnno class * {*;}
-keep @com.xiaojinzi.component.anno.FragmentAnno class * {*;}
-keep @com.xiaojinzi.component.anno.ServiceAnno class * {*;}
-keep @com.xiaojinzi.component.anno.support.ComponentGeneratedAnno class * {*;}
-keep @com.xiaojinzi.component.anno.router.RouterApiAnno interface * {*;}
#保留生成的 Router Api
-keep class **.**RouterApiGenerated {*;}
#几个用户自定义或者自动生成到其他包下的应该不混淆
-keep class * implements com.xiaojinzi.component.impl.RouterInterceptor{*;}
-keep class * implements com.xiaojinzi.component.support.IBaseLifecycle{*;}
-keep class * implements com.xiaojinzi.component.application.IApplicationLifecycle{*;}
-keep class * implements com.xiaojinzi.component.service.IServiceLifecycle{*;}
-keep class * implements com.xiaojinzi.component.application.IComponentApplication{*;}
-keep class * implements com.xiaojinzi.component.support.Inject{*;}



