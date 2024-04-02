package com.example.storytime

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.RowId
import java.sql.Time
import java.time.temporal.TemporalAmount

@Entity(tableName = "bookLists")
data class BookList(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val label: String,
    val amount: Double,
    val description: String) {

}