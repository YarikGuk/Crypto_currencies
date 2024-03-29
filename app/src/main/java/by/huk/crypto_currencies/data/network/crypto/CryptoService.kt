package by.huk.crypto_currencies.data.network.crypto

import by.huk.crypto_currencies.data.entities.crypto.MarketChart
import by.huk.crypto_currencies.data.source.dto.crypto.CryptoResponse
import by.huk.crypto_currencies.data.source.dto.crypto.MarketChartResponse
import by.huk.crypto_currencies.ui.utils.PRICE_CHANGE
import by.huk.crypto_currencies.ui.utils.USD
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoService {

    @GET("coins/markets")
    suspend fun getCryptoCurrencyList(
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("vs_currency") vsCurrency: String = USD,
        @Query("sparkline") sparkline: Boolean = true,
        @Query("price_change_percentage") priceChange: String = PRICE_CHANGE,
        @Query("per_page") perPage: Int = 20,
    ): Response<CryptoResponse>

    @GET("coins/{id}/market_chart")
    suspend fun getMarketCharts(
        @Path("id") id: String,
        @Query("days") days:String,
        @Query("interval") interval:String? = null,
        @Query("vs_currency") vsCurrency: String = USD
    ): Response<MarketChartResponse>
}