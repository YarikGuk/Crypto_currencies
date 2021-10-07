package by.huk.crypto_currencies.data.source.db.dao

import androidx.room.*
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.entities.user.User
import com.google.android.material.circularreveal.CircularRevealHelper

@Dao
interface CryptoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(cryptoList:List<CryptoEntity>)

    @Query("SELECT * FROM crypto_list")
    suspend fun loadInitList():List<CryptoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user:User)

    @Query("SELECT * FROM user")
    suspend fun loadUser():User

    @Update
    suspend fun updateUser(user: User)
}