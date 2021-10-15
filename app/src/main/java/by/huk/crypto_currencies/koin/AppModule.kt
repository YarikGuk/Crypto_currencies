package by.huk.crypto_currencies.koin

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import by.huk.crypto_currencies.BuildConfig
import by.huk.crypto_currencies.MainActivity
import by.huk.crypto_currencies.MainViewModel
import by.huk.crypto_currencies.SplashViewModel
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.network.crypto.CryptoService
import by.huk.crypto_currencies.data.repository.CryptoRepository
import by.huk.crypto_currencies.data.repository.crypto.CryptoDataSource
import by.huk.crypto_currencies.data.source.db.dao.CryptoDao
import by.huk.crypto_currencies.data.source.db.dao.CryptoDao_Impl
import by.huk.crypto_currencies.data.source.db.database.CryptoDatabase
import by.huk.crypto_currencies.data.source.db.database.CryptoDatabase_Impl
import by.huk.crypto_currencies.data.source.dto.mappers.CryptoResponseMapper
import by.huk.crypto_currencies.data.source.dto.mappers.MarketChartResponseMapper
import by.huk.crypto_currencies.ui.details.DetailsViewModel
import by.huk.crypto_currencies.ui.home.HomeViewModel
import by.huk.crypto_currencies.ui.settings.SettingViewModel
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@JvmField
val viewModelModule = module {
    viewModel { MainViewModel(get())}
    viewModel { SplashViewModel(get())}
    viewModel { DetailsViewModel(get())}
    viewModel { HomeViewModel(get()) }
    viewModel { SettingViewModel(get()) }
}

val dataSourceModule = module {

    fun getDataSource(cryptoMapper:CryptoResponseMapper,marketChartResponseMapper: MarketChartResponseMapper,service: CryptoService):CryptoDataSource{
        return CryptoDataSource(cryptoMapper,marketChartResponseMapper,service)
    }
    fun getCryptoRepository(dataSource: CryptoDataSource,dao: CryptoDao):CryptoRepository{
        return CryptoRepository(dataSource,dao)
    }
    fun provideDB(context: Context):CryptoDatabase{
        return CryptoDatabase.provideDB(context)
    }
    fun getCryptoDao(db:CryptoDatabase):CryptoDao{
        return db.cryptoDao()
    }

    single { getCryptoRepository(get(),get())}
    single { getDataSource(get(),get(),get()) }
    single { CryptoResponseMapper() }
    single { MarketChartResponseMapper() }

    single { getCryptoDao(get()) }
    single { provideDB( androidContext() ) }
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
