# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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
# Keep data binding classes
-keep class androidx.databinding.** { *; }

# Keep generated data binding classes
-keep class com.example.horoscope.databinding.** { *; }

# Keep the default constructor for FragmentLoginBinding
-keepclassmembers class com.example.horoscope.databinding.* {
    <init>(android.view.View);
}

-keep class com.example.horoscope.databinding.* {
    public static com.example.horoscope.databinding.* inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
}