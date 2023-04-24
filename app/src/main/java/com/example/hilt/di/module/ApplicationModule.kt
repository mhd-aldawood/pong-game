package com.example.hilt.di.module


import com.example.hilt.BuildConfig
import com.example.hilt.data.api.ApiHelper
import com.example.hilt.data.api.ApiHelperImpl
import com.mindorks.framework.mvvm.data.api.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import dagger.hilt.InstallIn
import retrofit2.converter.moshi.MoshiConverterFactory
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    //
//    @Provides
//    @Singleton
//    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}
@Module
@InstallIn(SingletonComponent::class)
interface Provider {
    @Binds
    @Singleton
    fun providerHelper(apiHelper: ApiHelperImpl): ApiHelper
}