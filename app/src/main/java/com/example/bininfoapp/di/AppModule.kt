package com.example.bininfoapp.di

import android.app.Application
import androidx.room.Room
import com.example.bininfoapp.data.local.BinDatabase
import com.example.bininfoapp.data.remote.BinlistApi
import com.example.bininfoapp.data.repository.BinRepositoryImpl
import com.example.bininfoapp.domain.repository.BinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Предоставляет экземпляр Retrofit API с логированием.
     * Я добавил HttpLoggingInterceptor, чтобы видеть детали запроса в Logcat.
     */
    @Provides
    @Singleton
    fun provideBinlistApi(): BinlistApi {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BinlistApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BinlistApi::class.java)
    }

    /**
     * Предоставляет экземпляр базы данных Room.
     */
    @Provides
    @Singleton
    fun provideBinDatabase(app: Application): BinDatabase {
        return Room.databaseBuilder(
            app,
            BinDatabase::class.java,
            BinDatabase.DATABASE_NAME
        ).build()
    }

    /**
     * Предоставляет реализацию репозитория.
     * Hilt автоматически подставит сюда api и db из функций выше.
     */
    @Provides
    @Singleton
    fun provideBinRepository(api: BinlistApi, db: BinDatabase): BinRepository {
        return BinRepositoryImpl(api, db)
    }
}