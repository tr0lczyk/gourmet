package com.example.gourmetapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

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
    val modificationDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(modificationDate)
}