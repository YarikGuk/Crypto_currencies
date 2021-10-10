package by.huk.crypto_currencies.data.entities.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    var firstName: String,
    var secondName: String,
    var birthday: String,
    var photo: String,
    @PrimaryKey(autoGenerate = false)
    val id:Int = 0
)