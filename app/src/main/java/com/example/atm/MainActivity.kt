package com.example.atm

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem

import androidx.core.app.ActivityCompat //targetSdkVersion>23
import android.Manifest.permission.*  // 危險權限
import android.view.Menu
import android.widget.Toolbar
import com.example.atm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object{
        val RC_LOGIN = 30
        val REQUEST_CAMERA=50
    }
    var login = false
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


//        val permission =checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) //查詢APP是否有得到危險權限
        if (!login) {
            Intent(this, LoginActivity::class.java).apply {
                startActivityForResult(this, RC_LOGIN)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_camera ->{
                val camera = Intent(this, CameraActivity::class.java)
                startActivityForResult(camera, REQUEST_CAMERA)
//                Log.d(ContentValues.TAG, "123")
            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_LOGIN && resultCode == Activity.RESULT_OK) {
            val userid = data?.getStringExtra("LOGIN_USERID")
            val passwd = data?.getStringExtra("LOGIN_PASSWD")
            Log.d(ContentValues.TAG, "$userid/$passwd")
        }
    }


}