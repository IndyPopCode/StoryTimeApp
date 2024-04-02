package com.example.storytime

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class BookListAdapter(private var bookLists: List<BookList>) :
    RecyclerView.Adapter<BookListAdapter.BooklistHolder>() {

    class BooklistHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label: TextView = view.findViewById(R.id.label)
        val amount: TextView = view.findViewById(R.id.amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooklistHolder {
       val view : View = LayoutInflater.from(parent.context).inflate(R.layout.booklist_layout, parent,false)
        return BooklistHolder(view)
    }

    override fun onBindViewHolder(holder: BooklistHolder, position: Int) {
        val bookLists: BookList = bookLists[position]
        val context: Context = holder.amount.context

        if( bookLists.amount>=0){
            holder.amount.text = " %.2f".format(bookLists.amount)
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.pink))

        }
        holder.label.text = bookLists.label
    }

    override fun getItemCount(): Int {
        return bookLists.size
    }

    fun setData(bookLists: List<BookList>){
        this.bookLists = bookLists
        notifyDataSetChanged()
    }

}