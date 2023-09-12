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