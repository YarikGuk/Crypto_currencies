package by.huk.crypto_currencies.data.source.db.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
class SparklineTypeConverter {
    @TypeConverter
    fun stringToList(data:String?):List<Double>{
        if (data.isNullOrEmpty()) return emptyList()

        val listType = object : TypeToken<List<Double>>(){}.type
        return Gson().fromJson(data,listType)
    }
    @TypeConverter
    fun listToString(list:List<Double>):String{
        return Gson().toJson(list)
    }

}