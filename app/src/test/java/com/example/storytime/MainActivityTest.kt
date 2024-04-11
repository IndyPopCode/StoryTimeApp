package com.example.storytime

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.junit.Assert.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class MainActivityTest {

    private lateinit var mainActivity: MainActivity
    private lateinit var bookListAdapter: BookListAdapter
    private lateinit var recyclerView: RecyclerView

    @Before
    fun setup() {
        mainActivity = MainActivity()
        bookListAdapter = mock(BookListAdapter::class.java)
        recyclerView = mock(RecyclerView::class.java)
      //  mainActivity.bookListAdapter = bookListAdapter
      //  mainActivity.recyclerView = recyclerView

    }

    @Test
    fun `test updateTotalTime`() {
        val bookList = listOf(
            BookList(1, "Book", 50.00,"good"),
            BookList(2, "Book 2", 45.00, "good"),
            BookList(3, "Book 3", 60.00, "good")
        )
        mainActivity.milestone = 100.0

        // When
        mainActivity.updateTotalTime(bookList)

        // Then
        assertEquals("135.00 Min", mainActivity.findViewById<TextView>(R.id.totalTime).text)
    }
}