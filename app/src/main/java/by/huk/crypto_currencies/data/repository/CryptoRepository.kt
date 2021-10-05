package by.huk.crypto_currencies.data.repository

import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.repository.crypto.CryptoDataSource
import by.huk.crypto_currencies.data.source.db.dao.CryptoDao
import by.huk.crypto_currencies.data.source.db.database.CryptoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CryptoRepository(
    private val cryptoDataSource: CryptoDataSource,
    private val dao: CryptoDao,
) {

    val ioScope = CoroutineScope(Dispatchers.IO)


    suspend fun loadCryptoList(order: String, page: Int): List<CryptoEntity> {
        return cryptoDataSource.getCryptoList(order, page)
    }
    fun insertCryptoList(list: List<CryptoEntity>){
        ioScope.launch { dao.insertList(list) }
    }
    suspend fun loadInitListFromDB():List<CryptoEntity>{
        return ioScope.async { dao.loadInitList() }.await()
    }
}