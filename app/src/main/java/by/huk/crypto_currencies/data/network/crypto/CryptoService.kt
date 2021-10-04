package by.huk.crypto_currencies.data.network.crypto

import by.huk.crypto_currencies.data.source.dto.crypto.CryptoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoService {

    @GET("coins/markets")
    suspend fun getCryptoCurrencyList(
        @Query("vs_currency") vsCurrency: String,
        @Query("order") order:String,
        @Query("per_page") perPage:Int,
        @Query("page") page:Int,
        @Query("sparkline") sparkline:Boolean,
        @Query("price_change_percentage") priceChange:String
    ):Response<CryptoResponse>
}