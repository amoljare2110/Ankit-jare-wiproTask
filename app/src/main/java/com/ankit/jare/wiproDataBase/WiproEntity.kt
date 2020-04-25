package com.ankit.jare.wiproDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wipro_app")
data class WiproEntity(

        @PrimaryKey
        @ColumnInfo(name = "title")
        var title: String,

        @ColumnInfo(name = "description")
        var description: String,

        @ColumnInfo(name = "imageHref")
        var imageHref: String,

        @ColumnInfo(name = "headerTitle")
        var headerTitle: String
)