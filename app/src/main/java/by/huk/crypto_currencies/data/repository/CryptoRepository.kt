package by.huk.crypto_currencies.data.repository

import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.network.crypto.CryptoService
import by.huk.crypto_currencies.data.repository.crypto.CryptoDataSource

class CryptoRepository (
    private val cryptoDataSource: CryptoDataSource
        ){
    suspend fun loadCryptoList(vsCurrency:String,order:String,perPage:Int,page: Int,sparkline:Boolean,priceChange:String):List<CryptoEntity>{
        return cryptoDataSource.getCryptoList(vsCurrency,order,perPage,page,sparkline,priceChange)
    }
}