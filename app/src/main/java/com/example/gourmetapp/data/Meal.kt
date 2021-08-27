package com.example.gourmetapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "meal_table")
@Parcelize
data class Meal(
        @Json(name = "description")
        val description: String,
        @Json(name = "image_url")
        val imageUrl: String,
        @Json(name = "modificationDate")
        val modificationDate: String,
        @Json(name = "orderId")
        @PrimaryKey
        val orderId: Int,
        @Json(name = "title")
        val title: String
) : Parcelable {
    fun getModificationDateFormatted(): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val newDate = originalFormat.parse(modificationDate)
        val targetFormat = SimpleDateFormat("EEE dd MMM, yyyy",Locale.ENGLISH)
        return targetFormat.format(newDate!!)
    }

    val descriptionUrl:String get() = "http${description.split("http").last()}"

    val descriptionOnly: String get() = description.split("http").first()
}