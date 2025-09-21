package com.example.grabit.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.grabit.data.config.AppConfig.BASE_URL
import com.example.grabit.data.retrofit.ApiCart
import com.example.grabit.data.retrofit.AuthApi
import com.example.grabit.data.retrofit.ProductApi
import com.example.grabit.data.retrofit.UserApi
import com.example.grabit.data.storage.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL) // API base
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun providedUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApiCart(retrofit: Retrofit): ApiCart {
        return retrofit.create(ApiCart::class.java)
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun providedPreferences(dataStore: DataStore<Preferences>): UserPreferences {
        return UserPreferences(preferencesDataStore = dataStore)
    }
}