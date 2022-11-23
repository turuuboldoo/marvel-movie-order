package mn.turbo.marvel.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import mn.turbo.marvel.common.ConnectivityObserver
import mn.turbo.marvel.common.Constant
import mn.turbo.marvel.common.NetworkConnectionObserver
import mn.turbo.marvel.data.remote.MarvelApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMarvelApi(
        okHttpClient: OkHttpClient
    ): MarvelApi {
        return Retrofit
            .Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                connectTimeout(30, TimeUnit.SECONDS)
                writeTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)
                addInterceptor(
                    HttpLoggingInterceptor()
                        .apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                )
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkConnection(
        application: Application
    ): ConnectivityObserver {
        return NetworkConnectionObserver(application.applicationContext)
    }
}
