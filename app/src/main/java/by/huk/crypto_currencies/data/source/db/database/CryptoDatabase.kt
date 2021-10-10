package by.huk.crypto_currencies.data.source.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.entities.user.User
import by.huk.crypto_currencies.data.source.db.dao.CryptoDao

@Database(entities = [CryptoEntity::class,User::class], version = 1)
@TypeConverters(SparklineTypeConverter::class)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao

    companion object {
        private var INSTANCE: CryptoDatabase? = null

        fun provideDB(context: Context): CryptoDatabase {
            return if (INSTANCE != null) INSTANCE as CryptoDatabase
            else {
                INSTANCE =
                    Room.databaseBuilder(context, CryptoDatabase::class.java, "database").build()
                INSTANCE as CryptoDatabase
            }
        }
    }
}
