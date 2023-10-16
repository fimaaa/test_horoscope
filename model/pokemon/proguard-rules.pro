# Preserve annotations
-keepattributes *Annotation*

# Preserve fields annotated with @SerializedName
-keepclassmembers class com.example.model.pokemon.* {
    @com.google.gson.annotations.SerializedName *;
}

-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }
-keep class org.apache.commons.** { *; }
-keep class org.joda.time.** { *; }
-keep class javax.** { *; }
-keep class kotlin.** { *; }
-keep class com.example.model.pokemon.** { *; }
-keep class com.example.model.pokemon.** { *; }
-keep class com.example.model.pokemon.** { *; }

-dontwarn javax.**
-dontwarn kotlin.**
-dontwarn sun.misc.**