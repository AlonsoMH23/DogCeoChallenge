package com.example.dogceochallenge.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.dogceochallenge.BuildConfig
import com.example.dogceochallenge.base.ViewModelFactory
import com.example.dogceochallenge.data.repository.source.BreedsDataRepository
import com.example.dogceochallenge.data.repository.source.remote.BreedsApiDataSource
import com.example.dogceochallenge.data.repository.source.remote.BreedsDataSource
import com.example.dogceochallenge.data.repository.source.remote.api.ApiService
import com.example.dogceochallenge.data.repository.source.remote.mappers.ResponseMapper
import com.example.dogceochallenge.domain.repository.BreedsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_API_URL = "https://dog.ceo/dog-api/"

val module = module {

    single {
        androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
            })
            .callTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>()
            .create(ApiService::class.java) as ApiService
    }

    single {
        ResponseMapper
    }

    factory<BreedsDataSource> { BreedsApiDataSource(get()) }

    single<BreedsRepository> { BreedsDataRepository(get(), get()) }

    single { ViewModelFactory(get()) }

}

