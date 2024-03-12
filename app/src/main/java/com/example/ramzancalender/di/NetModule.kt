package com.example.ramzancalender.di

import android.content.Context
import com.example.ramzancalender.BuildConfig
import com.example.ramzancalender.helper.NetworkHelper
import com.example.ramzancalender.network.PrayerTimesApi
import com.example.ramzancalender.network.Repositary
import com.example.ramzancalender.network.RepositaryImpl
import com.example.ramzancalender.room.PostDb
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        baseUrl: String,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(client)
            addConverterFactory(MoshiConverterFactory.create(moshi))
        }.build()

    @Provides
    @Singleton
    fun provideHttpLogging() =
        HttpLoggingInterceptor().apply {
            level = when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
        }.build()

    @Provides
    @Singleton
    fun provideRepository(
        service: PrayerTimesApi,
        database: PostDb,
        networkHelper: NetworkHelper
    ): Repositary =
        RepositaryImpl(service, database, networkHelper)

    @Provides
    @Singleton
    fun provideNetworkHelper(
        @ApplicationContext context: Context
    ): NetworkHelper =
        NetworkHelper(context)


    @Provides
    @Singleton
    fun provideRestaurantApi(retrofit: Retrofit): PrayerTimesApi =
        retrofit.create(PrayerTimesApi::class.java)

    // Provide Moshi
    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder().apply {
            addLast(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
        }.build()
}


private const val BASE_URL = "https://api.aladhan.com/"
