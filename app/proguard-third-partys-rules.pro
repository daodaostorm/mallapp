# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\DELL\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# >>>>>>>>>>>>>>>>>>>>>>>>  Glide  Start >>>>>>>>>>>>>>>>>>>>>>>>
# Glide specific rules #
# https://github.com/bumptech/glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# >>>>>>>>>>>>>>>>>>>>>>>>  Glide  End >>>>>>>>>>>>>>>>>>>>>>>>

# >>>>>>>>>>>>>>>>>>>>>>>>  tencent  Start >>>>>>>>>>>>>>>>>>>>>>>>
-keep class com.tencent.stat.*{*;}
-keep class com.tencent.mid.*{*;}


##---------------Begin: proguard configuration for Gson ----------
-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}

-keepattributes Signature
-keepattributes *Annotation*
-keep public class com.project.mocha_patient.login.SignResponseData { private *; }

##---------------End: proguard configuration for Gson ----------

#忽略警告
-ignorewarnings

# >>>>>>>>>>>>>>>>>>>>>>>>  OkHttp  Start >>>>>>>>>>>>>>>>>>>>>>>>
# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

# OkHttp3
-dontwarn okhttp3.**
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**
-keep class okio.** {*;}
# >>>>>>>>>>>>>>>>>>>>>>>>  OkHttp  End >>>>>>>>>>>>>>>>>>>>>>>>

# >>>>>>>>>>>>>>>>>>>>>>>>  Rxjava  Start >>>>>>>>>>>>>>>>>>>>>>>>
-dontwarn sun.misc.**
-dontwarn org.apache.http.**

-keep class rx.**.

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
# >>>>>>>>>>>>>>>>>>>>>>>>  Rxjava  End >>>>>>>>>>>>>>>>>>>>>>>>
# databinding
#-dontwarn android.databinding.**
#-keep class android.databinding.** { *; }

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
#-keepattributes Signature-keepattributes Exceptions
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
-keep class org.xz_sale.entity.**{*;}

-dontwarn android.net.compatibility.**
-dontwarn android.net.http.**
-dontwarn com.android.internal.http.multipart.**
-dontwarn org.apache.commons.**
-dontwarn org.apache.**
-keep class android.net.compatibility.**{*;}
-keep class android.net.http.**{*;}
-keep class com.android.internal.http.multipart.**{*;}
-keep class org.apache.commons.**{*;}
-keep class org.apache.**{*;}

-dontwarn org.apache.log4j.**
-keep class  org.apache.log4j.** { *;}

#-libraryjars libs/kandy-1.8.49.jar
-keep  class com.genband.** { *; }
#-keep  class com.a.** { *; }
-dontwarn  com.genband.**
#-dontwarn  com.a.**

-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**

-keep class com.txt.library.base.** { *; }
-dontwarn com.txt.library.base.**

-keep class com.ran.mall.entity.constant.SPConstant{*;}
-keep class com.ran.mall.entity.bean.**{*;}

-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }