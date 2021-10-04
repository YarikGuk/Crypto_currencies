package by.huk.crypto_currencies.data.source.dto.crypto


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

class CryptoResponse : ArrayList<CryptoResponse.CryptoResponseItem>() {
    data class CryptoResponseItem(
        @SerializedName("current_price")
        val currentPrice: Double? = null,
        @SerializedName("id")
        val id: String? = null,
        @SerializedName("image")
        val image: String? = null,
        @SerializedName("market_cap")
        val marketCap: Long? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("price_change_percentage_1y_in_currency")
        val priceChangePercentage1yInCurrency: Double? = null,
        @SerializedName("price_change_percentage_24h_in_currency")
        val priceChangePercentage24hInCurrency: Double? = null,
        @SerializedName("price_change_percentage_30d_in_currency")
        val priceChangePercentage30dInCurrency: Double? = null,
        @SerializedName("price_change_percentage_7d_in_currency")
        val priceChangePercentage7dInCurrency: Double? = null,
        @SerializedName("sparkline_in_7d")
        val sparklineIn7d: SparklineIn7d? = null,
        @SerializedName("symbol")
        val symbol: String? = null,

        ) {
        data class SparklineIn7d(
            @SerializedName("price")
            val price: List<Double>? = null,
        )
    }
}