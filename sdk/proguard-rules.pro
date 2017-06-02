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
-dontwarn com.google.android.exoplayer.**
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

# MoPub
-dontwarn com.mopub.**
-dontwarn com.google.android.exoplayer.**
-dontwarn com.mopub.nativeads.**
-keepclassmembers class com.mopub.** { public *; }
-keep class com.mopub.**
-keep public class android.webkit.JavascriptInterface {}

# StartApp
-dontwarn com.startapp.**
-keep class com.startapp.** { *; }
-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,
-dontwarn android.webkit.JavascriptInterface
-dontwarn com.startapp.**

# Explicitly keep any custom event classes in any package.
-keep class * extends com.mopub.mobileads.CustomEventBanner {}
-keep class * extends com.mopub.mobileads.CustomEventInterstitial {}
-keep class * extends com.mopub.nativeads.CustomEventNative {}
-keep class * extends com.mopub.nativeads.CustomEventRewardedAd {}

# Keep methods that are accessed via reflection
-keepclassmembers class ** { @com.mopub.common.util.ReflectionTarget *; }

# Support for Android Advertiser ID.
-keep class com.google.android.gms.common.GooglePlayServicesUtil {*;}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {*;}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {*;}

# Support for Google Play Services
# http://developer.android.com/google/play-services/setup.html
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

# Legacy
-keep class org.apache.http.** { public *; }
-dontwarn org.apache.http.**
-dontwarn android.net.http.**

# Google Play Services library 9.0.0 only
-dontwarn android.security.NetworkSecurityPolicy
-keep public @com.google.android.gms.common.util.DynamiteApi class * { *; }