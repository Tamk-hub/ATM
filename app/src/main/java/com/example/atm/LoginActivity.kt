package com.example.atm

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.atm.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userid = getSharedPreferences("atm",Context.MODE_PRIVATE).getString("PREF_USERID","")
        binding.edUesrid.setText(userid)
    }

    fun login(view: View) {
        val userid = binding.edUesrid.text.toString()
//        Log.d(ContentValues.TAG, userid)
        val passwd = binding.edPassword.text.toString()
//        Log.d(ContentValues.TAG, passwd)
        if (userid == "jack" && passwd == "1234") {
            getSharedPreferences("atm", Context.MODE_PRIVATE).edit().putString("PREF_USERID", userid).apply()
            Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show()
            val intent = Intent()
            intent.putExtra("LOGIN_USERID", userid)
            intent.putExtra("LOGIN_PASSWD", passwd)
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            AlertDialog.Builder(this).setTitle("ATM").setMessage("登入失敗")
                .setPositiveButton("OK", null).show()
        }
    }

    fun cancel(view: View) {

    }
}


