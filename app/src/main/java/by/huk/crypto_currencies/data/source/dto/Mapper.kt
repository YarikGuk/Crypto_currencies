package by.huk.crypto_currencies.data.source.dto

interface Mapper<R,E> {
    fun map (from:R):E
}