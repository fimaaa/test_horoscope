# Keep data binding classes
-keep class androidx.databinding.** { *; }

# Keep generated data binding classes
-keep class com.example.pokemon.databinding.** { *; }

# Keep the default constructor for FragmentLoginBinding
-keepclassmembers class com.example.pokemon.databinding.* {
    <init>(android.view.View);
}

-keep class com.example.pokemon.databinding.* {
    public static com.example.pokemon.databinding.* inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
}