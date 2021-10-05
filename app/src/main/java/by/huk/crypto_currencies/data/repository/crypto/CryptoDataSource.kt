package by.huk.crypto_currencies.data.repository.crypto

import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.network.crypto.CryptoService
import by.huk.crypto_currencies.data.source.dto.mappers.CryptoResponseMapper

class CryptoDataSource (
    private val cryptoResponseMapper: CryptoResponseMapper,
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
}