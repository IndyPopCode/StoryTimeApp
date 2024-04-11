package com.example.storytime

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var deletedBooklist: BookList
    private lateinit var bookList: List<BookList>
    private lateinit var bookListAdapter: BookListAdapter
    private lateinit var linearlayoutManager: LinearLayoutManager
    private lateinit var db : AppDatabase
    var milestone: Double = 1000.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bookList = arrayListOf()

        bookListAdapter= BookListAdapter(bookList)
        linearlayoutManager= LinearLayoutManager(this)

        db = Room.databaseBuilder(this,
            AppDatabase::class.java,
            "bookList").build()

        findViewById<RecyclerView> (R.id.recyclerview).apply {
            adapter = bookListAdapter
            layoutManager= linearlayoutManager
        }
        val milestoneEditText = findViewById<EditText>(R.id.goalTime)

        milestoneEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                milestone = s.toString().toDoubleOrNull() ?: 0.0

                }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        //swipe to delete
        val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteBookList(bookList[viewHolder.adapterPosition])
            }
        }
        val swipeHelper= ItemTouchHelper(itemTouchHelper)
        swipeHelper.attachToRecyclerView(findViewById<RecyclerView>(R.id.recyclerview))


        findViewById<FloatingActionButton>(R.id.addButton)
            .setOnClickListener {
                val intent=Intent(this, AddBookActivity::class.java )
                startActivity(intent)
            }
    }
    private fun fetchAll(){
        GlobalScope.launch {
            bookList = db.bookListDao().getAll()

            runOnUiThread {
              updateTotalTime(bookList)
                bookListAdapter.setData(bookList)

            }
        }
    }

    fun updateTotalTime(bookList: List<BookList>) {
        val totalAmount = this.bookList.map {it.amount }.sum()

        findViewById<TextView>(R.id.totalTime).text = "%.2f Min".format(totalAmount)

        if (totalAmount >= milestone) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Milestone Reached!")
                .setMessage("Congratulations! You have reached a milestone of $milestone minutes reading time. KEEP IT UP!")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private  fun deleteBookList(bookLists: BookList){
        deletedBooklist = bookLists


        GlobalScope.launch {
            db.bookListDao().delete(bookLists)

            bookList = bookList.filter {it.id !=bookLists.id  }
            runOnUiThread{
                updateTotalTime(bookList)

            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchAll()
    }
}
