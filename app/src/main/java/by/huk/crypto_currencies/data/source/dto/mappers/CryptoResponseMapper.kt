package by.huk.crypto_currencies.data.source.dto.mappers

import androidx.compose.ui.text.toUpperCase
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.source.dto.Mapper
import by.huk.crypto_currencies.data.source.dto.crypto.CryptoResponse

class CryptoResponseMapper() : Mapper<CryptoResponse.CryptoResponseItem, CryptoEntity> {
    override fun map(from: CryptoResponse.CryptoResponseItem): CryptoEntity {
        return CryptoEntity(
            id = from.id.orEmpty(),
            name = from.name.orEmpty(),
            symbol = from.symbol?.uppercase().orEmpty(),
            currentPrice = from.currentPrice ?: 0.0,
            image = from.image.orEmpty(),
            marketCap = from.marketCap ?: 0L,
            priceChangePercentage1yInCurrency = from.priceChangePercentage1yInCurrency ?: 0.0,
            priceChangePercentage24hInCurrency = from.priceChangePercentage24hInCurrency ?: 0.0,
            priceChangePercentage30dInCurrency = from.priceChangePercentage30dInCurrency ?: 0.0,
            priceChangePercentage7dInCurrency = from.priceChangePercentage7dInCurrency ?: 0.0,
            sparklineIn7d = from.sparklineIn7d?.price ?: emptyList(),
        )
    }
}