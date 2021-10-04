package by.huk.crypto_currencies.koin

import android.app.Application
import by.huk.crypto_currencies.BuildConfig
import by.huk.crypto_currencies.MainActivity
import by.huk.crypto_currencies.MainViewModel
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.network.crypto.CryptoService
import by.huk.crypto_currencies.data.repository.CryptoRepository
import by.huk.crypto_currencies.data.repository.crypto.CryptoDataSource
import by.huk.crypto_currencies.data.source.dto.mappers.CryptoResponseMapper
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@JvmField
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

val appModule = module {


    fun getDataSource(mapper:CryptoResponseMapper,service: CryptoService):CryptoDataSource{
        return CryptoDataSource(mapper,service)
    }
    fun getCryptoRepository(dataSource: CryptoDataSource):CryptoRepository{
        return CryptoRepository(dataSource)
    }


    single { getCryptoRepository(get())}
    single { getDataSource(get(),get()) }
    single { CryptoResponseMapper() }


}
val apiModule = module {
    fun provideApi(retrofit: Retrofit):CryptoService {
        return retrofit.create()
    }
    single { provideApi(get()) }
}
val networkModule = module {
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .client(client)
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    fun getInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
        }

    }
    single { getInterceptor() }
    single { getClient(get()) }
    single { provideRetrofit(get())}
}
