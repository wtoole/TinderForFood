package edu.towson.cosc435.sabol.tinderforfood.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*


@Entity
data class Recipe (
    @PrimaryKey
    val id: UUID,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "artist")
    val artist: String,
    @ColumnInfo(name = "track_num")
    @SerializedName("track_num")
    val trackNum: Int,
    @ColumnInfo(name = "is_awesome")
    @SerializedName("is_awesome")
    val isAwesome: Boolean
)