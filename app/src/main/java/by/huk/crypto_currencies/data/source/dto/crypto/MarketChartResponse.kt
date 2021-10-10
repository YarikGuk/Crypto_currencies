package by.huk.crypto_currencies.data.source.dto.crypto


import android.graphics.Paint
import com.google.gson.annotations.SerializedName

data class MarketChartResponse(
    @SerializedName("prices")
    val prices: List<List<Double>>?
)