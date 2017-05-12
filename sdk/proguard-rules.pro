# MilkyFox
-keep public class com.milkyfox.sdk.common.** { public *; }
-keepattributes EnclosingMethod

# Facebook
-dontwarn com.facebook.**
-keep public class com.facebook.** { public *; }
-keepclasseswithmembers class * {
    *** *onError(...);
}
-keepclasseswithmembers class * {
    *** *onAdLoaded(...);
}
-keepclasseswithmembers class * {
    *** *onAdClicked(...);
}

# Applovin
-keep class com.applovin.** { *; }
-dontwarn com.applovin.**

# Admob
-keep class com.google.android.gms.ads.** { *; }
-keep public class com.google.ads.mediation.* { public *; }

# Unity Ads
-keepattributes JavascriptInterface
-keepattributes SourceFile,LineNumberTable
-keep class com.unity3d.ads.** { *; }
-keep class com.applifier.** { *; }
-keep class android.webkit.JavascriptInterface {*;}


# Adcolony
-dontnote com.immersion.**
-dontwarn android.webkit.**
-keep class com.adcolony.** { public *; }
-dontwarn com.adcolony.**
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Vungle
-dontwarn com.vungle.**
-keep class com.vungle.** { *; }
-keep class javax.inject.*
-keepattributes *Annotation*, Signature
-keep class dagger.*

# Legacy
-keep class org.apache.http.** { public *; }
-dontwarn org.apache.http.**
-dontwarn android.net.http.**

# Google Play Services library 9.0.0 only
-dontwarn android.security.NetworkSecurityPolicy
-keep public @com.google.android.gms.common.util.DynamiteApi class * { *; }