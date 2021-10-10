package by.huk.crypto_currencies.data.source.dto.mappers

import android.provider.ContactsContract
import android.text.TextUtils.indexOf
import by.huk.crypto_currencies.data.entities.crypto.MarketChart
import by.huk.crypto_currencies.data.source.dto.Mapper
import by.huk.crypto_currencies.data.source.dto.crypto.CryptoResponse
import by.huk.crypto_currencies.data.source.dto.crypto.MarketChartResponse
import com.yabu.livechart.model.DataPoint
import kotlin.concurrent.fixedRateTimer

class MarketChartResponseMapper():Mapper<List<Double>?,MarketChart>{

    override fun map(from: List<Double>?): MarketChart{
        return MarketChart(
            time = from?.get(0)?.toLong()?:0L,
            price = from?.get(1)?.toFloat()?:0F
        )
    }


}