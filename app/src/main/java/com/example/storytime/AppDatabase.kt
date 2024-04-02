package com.example.storytime

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(BookList::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookListDao(): BookListDao
}