package com.example.atm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.reflect.Executable
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ExpenseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        val expenseData = arrayListOf<Expense>(
            Expense("2021/02/01", "Lunch", 120),
            Expense("2021/02/02", "停車費", 60),
            Expense("2021/02/05", "日用品", 215),
            Expense("2021/02/07","Parking",55)
        )
        val fab =findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val database:ExpenseDatabase = Room.databaseBuilder(this,ExpenseDatabase::class.java,"expense.db")
                .build()  //this代表 目前ExpenseActivity , ExpenseDatabase::class.java產生的資料庫類別 ,  expense.db代表SQLite資料庫名稱

            Executors.newSingleThreadExecutor().execute{  //使用JAVA的Executors類別 提供另一個執行續的工作
                database.expenseDao().add(expenseData[0])
            }

        }



    }
}