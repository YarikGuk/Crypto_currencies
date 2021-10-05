package by.huk.crypto_currencies.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import com.google.android.material.circularreveal.CircularRevealHelper

@Dao
interface CryptoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(cryptoList:List<CryptoEntity>)
    @Query("SELECT * FROM crypto_list")
    suspend fun loadInitList():List<CryptoEntity>
}