package com.example.atm


import android.annotation.SuppressLint
import android.content.ContentValues

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout.TabGravity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Executable
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ExpenseActivity : AppCompatActivity() {

    private lateinit var database: ExpenseDatabase
    private lateinit var recycler: RecyclerView
    val expenseData = arrayListOf<Expense>(
        Expense("2021/02/01", "Lunch", 120),
        Expense("2021/02/02", "停車費", 60),
        Expense("2021/02/05", "日用品", 215),
        Expense("2021/02/07", "Parking", 55)
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        database = Room.databaseBuilder(this, ExpenseDatabase::class.java, "expense.db")
            .build()
        //Query expenses

        val fab = findViewById<FloatingActionButton>(R.id.fab2)
        recycler = findViewById(R.id.recycler2)

        CoroutineScope(Dispatchers.IO).launch {
            val expenses = database.expenseDao().getAll()
            Log.d(ContentValues.TAG, expenses.size.toString())
            val adapter = object : RecyclerView.Adapter<ExpenseViewHolder>() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                        : ExpenseViewHolder {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.expense_row, parent, false)
                    return ExpenseViewHolder(view)
                }

                override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
                    val exp = expenses.get(position)
                    holder.date.setText(exp.date)
                    holder.info.setText(exp.info)
                    holder.amount.setText(exp.amount.toString())
                }

                override fun getItemCount(): Int {
                    return expenses.size
                }
            }
            runOnUiThread {
                recycler.setHasFixedSize(true)
                recycler.layoutManager = LinearLayoutManager(this@ExpenseActivity)
                recycler.adapter = adapter
            }
        }
        fab.setOnClickListener {
            Executors.newSingleThreadExecutor().execute {
                database.expenseDao().add(expenseData[0])
                database.expenseDao().add(expenseData[1])
                database.expenseDao().add(expenseData[2])
                database.expenseDao().add(expenseData[3])
            }
        }


    }

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val date = itemView.findViewById<TextView>(R.id.exp_date)
        val info = itemView.findViewById<TextView>(R.id.exp_info)
        val amount = itemView.findViewById<TextView>(R.id.exp_amount)
    }
}