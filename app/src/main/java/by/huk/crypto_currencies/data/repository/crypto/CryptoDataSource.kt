package by.huk.crypto_currencies.data.repository.crypto

import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.entities.crypto.MarketChart
import by.huk.crypto_currencies.data.network.crypto.CryptoService
import by.huk.crypto_currencies.data.source.dto.crypto.MarketChartResponse
import by.huk.crypto_currencies.data.source.dto.mappers.CryptoResponseMapper
import by.huk.crypto_currencies.data.source.dto.mappers.MarketChartResponseMapper
import com.google.gson.Gson
import com.yabu.livechart.model.DataPoint

class CryptoDataSource (
    private val cryptoResponseMapper: CryptoResponseMapper,
    private val marketChartResponseMapper: MarketChartResponseMapper,
    private val cryptoApi: CryptoService
    ) {


    suspend fun getCryptoList(order: String,page: Int): List<CryptoEntity> {
        val response = cryptoApi.getCryptoCurrencyList(order,page)

        return if (response.isSuccessful) {
            response.body()?.map {
                cryptoResponseMapper.map(it)
            }.orEmpty()
        } else {
            throw Throwable(response.errorBody().toString())
        }
    }
    suspend fun getMarketChart(id:String,days:String,interval:String? = null): List<MarketChart>{
        val response = cryptoApi.getMarketCharts(id,days,interval)

        return if (response.isSuccessful){

            response.body()?.prices?.map {
                marketChartResponseMapper.map(it)
            }.orEmpty()
        }else{
            throw Throwable(response.errorBody().toString())
        }
    }
}