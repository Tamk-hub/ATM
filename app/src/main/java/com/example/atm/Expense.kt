package com.example.atm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Entity實體類別
@Entity
class Expense(
    @ColumnInfo(name = "dateAt")
    var date: String,
    var info: String,
    var amount: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}