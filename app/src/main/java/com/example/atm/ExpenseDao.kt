package com.example.atm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//DAO資料存取類別
@Dao
interface ExpenseDao {
    @Insert  //新增筆數的方法前要加
    fun add(expense: Expense)  //新增消費紀錄

    @Query("select * from Expense")  //回傳消費紀錄的集合
    fun getAll():List<Expense>

}