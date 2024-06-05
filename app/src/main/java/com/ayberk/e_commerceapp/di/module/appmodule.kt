package com.ayberk.e_commerceapp.di.module

import android.content.Context
import androidx.room.Room
import com.ayberk.e_commerceapp.common.Constans.BASE_URL
import com.ayberk.e_commerceapp.data.source.FavoriteDao
import com.ayberk.e_commerceapp.data.source.FavoritesRoomDB
import com.ayberk.e_commerceapp.domain.retrofit.Authenticator
import com.ayberk.e_commerceapp.domain.retrofit.RetrofitRep
import com.ayberk.e_commerceapp.domain.retrofit.RetrofitServiceIns
import com.ayberk.e_commerceapp.domain.usecase.UpsertBagProducts
import com.ayberk.e_commerceapp.domain.usecase.event.BagEvent
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("store", "mavi") // Burada başlığı ekle
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun getRetrofitServiceInstance(retrofit: Retrofit): RetrofitServiceIns {
        return retrofit.create(RetrofitServiceIns::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    @Provides
    @Singleton
    fun provideAuthenticator(): Authenticator {
        return AuthClass()
    }

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    fun provideBagRoomDAO(bagRoomDB: FavoritesRoomDB): FavoriteDao {
        return bagRoomDB.productFavoriteDAO()
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): FavoritesRoomDB {
        return Room.databaseBuilder(
            context,
            FavoritesRoomDB::class.java,
            "bagdatabase.db"
        ).build()
    }

    @Provides
    fun provideUpsertRocket(retrofitRepository: RetrofitRep): UpsertBagProducts {
        return UpsertBagProducts(retrofitRepository)
    }
}
