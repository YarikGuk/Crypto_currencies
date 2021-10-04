package by.huk.crypto_currencies.data.entities.crypto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import by.huk.crypto_currencies.data.source.db.database.SparklineTypeConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "crypto_list")
data class CryptoEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val symbol: String,
    val currentPrice: Double,
    val image: String,
    val marketCap: Long,
    val priceChangePercentage1yInCurrency: Double,
    val priceChangePercentage24hInCurrency: Double,
    val priceChangePercentage30dInCurrency: Double,
    val priceChangePercentage7dInCurrency: Double,
    @TypeConverters(SparklineTypeConverter::class)
    val sparklineIn7d: List<Double>,

)
