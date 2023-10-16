package com.example.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.network.BuildConfig
import com.example.network.adapter.JSONObjectAdapter
import com.example.network.service.PokemonService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun clientBasic(): String = ""

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(JSONObjectAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun providesMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Singleton
    @Provides
    @Named("interceptor_chucker")
    fun provideChuckerInterceptor(
        @ApplicationContext mContext: Context
    ): Interceptor = ChuckerInterceptor.Builder(mContext)
        .collector(ChuckerCollector(mContext))
        .maxContentLength(250000L)
        .redactHeaders(emptySet())
        .alwaysReadResponseBody(true)
        .build()

    @Provides
    @Singleton
    @Named("client_basic")
    fun provideOkHttpClientBasic(
//        basicAuth: String,
        @Named("interceptor_chucker") interceptorChucker: Interceptor
    ): OkHttpClient = run {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val language = if (Locale.getDefault().language == "in") "id" else "en"
                val request = chain.request()
                val requestBuilder = request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept-Language", language)
                    .addHeader("X-version", BuildConfig.VERSION_CODE.toString())
//                if (basicAuth.isNotEmpty() && request.header("Authorization") == null) {
//                    requestBuilder.addHeader(
//                        "Authorization", basicAuth
//                    )
//                }
                chain.proceed(requestBuilder.build())
            }
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
            )
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor(interceptorChucker)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
        okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    @Named("pokemon")
    fun provideRetrofitPokemon(
        @Named("client_basic") okHttpClient: OkHttpClient,
        converterFactory: MoshiConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(getPokemonUrl())
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun providePokemonService(@Named("pokemon") retrofit: Retrofit): PokemonService =
        retrofit.create(PokemonService::class.java)
}