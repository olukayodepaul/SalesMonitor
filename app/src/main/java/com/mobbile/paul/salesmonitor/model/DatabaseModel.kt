package com.mobbile.paul.salesmonitor.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "questions")
data class Questions (
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
)