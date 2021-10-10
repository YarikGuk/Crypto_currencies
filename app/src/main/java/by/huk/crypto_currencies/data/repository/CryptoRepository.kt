package by.huk.crypto_currencies.data.repository

import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.entities.crypto.MarketChart
import by.huk.crypto_currencies.data.entities.user.User
import by.huk.crypto_currencies.data.repository.crypto.CryptoDataSource
import by.huk.crypto_currencies.data.source.db.dao.CryptoDao
import by.huk.crypto_currencies.data.source.db.database.CryptoDatabase
import com.google.android.material.transition.MaterialArcMotion
import com.yabu.livechart.model.DataPoint
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
    suspend fun loadMarketChart(id:String,days:String,interval:String? = null): List<MarketChart> {
        return cryptoDataSource.getMarketChart(id,days,interval)
    }
    fun insertCryptoList(list: List<CryptoEntity>){
        ioScope.launch { dao.insertList(list) }
    }
    fun insertUser(user: User){
        ioScope.launch { dao.insertUser(user) }
    }
    fun updateUser(user: User){
        ioScope.launch { dao.updateUser(user) }
    }
    suspend fun loadInitListFromDB():List<CryptoEntity>{
        return ioScope.async { dao.loadInitList() }.await()
    }
    suspend fun loadUserFromDB():User{
        return ioScope.async { dao.loadUser() }.await()
    }

}