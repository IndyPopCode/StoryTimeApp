package com.example.storytime

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface BookListDao {
    @Query("SELECT * from bookLists")
    fun getAll(): List<BookList>

    @Insert
    fun insertAll(vararg bookList: BookList)

    @Delete
    fun delete(bookList: BookList)

    @Update
    fun update(vararg bookList: BookList)
}