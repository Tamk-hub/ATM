package com.example.atm

import androidx.room.Database
import androidx.room.RoomDatabase
//@Database抽象類別取得DAO物件的抽象方法
@Database(entities = arrayOf(Expense::class), version = 1)
abstract class ExpenseDatabase:RoomDatabase(){    //抽象類別，並繼承RoomDatabase
    abstract fun expenseDao(): ExpenseDao  //取得DAO物件的抽象方法
}