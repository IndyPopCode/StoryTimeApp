package com.example.storytime

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddBookActivity : AppCompatActivity() {

    private lateinit var mediaPlayer:MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_book)

//Create a sound that plays when adding a new book
        mediaPlayer=MediaPlayer.create(this,R.raw.hooraysound)
        mediaPlayer.start()

        //Creating and error alert when fields are left empty to remind the user to add content
        findViewById<TextInputEditText>(R.id.labelInput).addTextChangedListener {
            if (it!!.isNotEmpty())
                findViewById<TextInputLayout>(R.id.labelLayout).error = null //error will occur if left empty
        }
        findViewById<TextInputEditText>(R.id.timeInput).addTextChangedListener {
                if (it!!.isNotEmpty())
                    findViewById<TextInputLayout>(R.id.timeLayout).error = null
            }

                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                    v.setPadding(
                        systemBars.left,
                        systemBars.top,
                        systemBars.right,
                        systemBars.bottom
                    )
                    insets

                }
            // Defining error messages for input fields
            findViewById<Button>(R.id.addBookButton).setOnClickListener {
                val label = findViewById<TextInputEditText>(R.id.labelInput).text.toString()
                val description  = findViewById<TextInputEditText>(R.id.commentInput).text.toString()
                val amount = findViewById<TextInputEditText>(R.id.timeInput).text.toString().toDoubleOrNull()

                if (label.isEmpty())
                    findViewById<TextInputLayout>(R.id.labelLayout).error = "Please enter book name"

                else if (amount == null)
                    findViewById<TextInputLayout>(R.id.timeLayout).error = "Please enter read time"
                else {
                    val bookList= BookList(0, label, amount, description)
                  insert(bookList)
                }
            }
            //adding a close button to go back to previous activity
            findViewById<ImageButton>(R.id.closeButton).setOnClickListener {
                finish()
            }
    }
    // this function is for inserting the book into the local room database for persistent storage
    private  fun insert(bookList: BookList){
        val db = Room.databaseBuilder(this,
            AppDatabase::class.java,
            "bookList").build()
        GlobalScope.launch {
            db.bookListDao().insertAll(bookList)
            finish()
        }

    }
    //Turning off the media player
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
