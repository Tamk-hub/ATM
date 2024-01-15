package com.example.atm

import android.Manifest.permission.READ_CONTACTS
import android.Manifest.permission.WRITE_CONTACTS
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.atm.databinding.ActivityLoginBinding
import com.example.atm.databinding.ActivityMaterialBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MaterialActivity : AppCompatActivity() {
    companion object {
        val REQUEST_CONTACTS = 100
    }
    val contacts = mutableListOf<Contact>()
    private lateinit var binding: ActivityMaterialBinding
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        recycler = findViewById(R.id.recycler)
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val permission = ActivityCompat.checkSelfPermission(this,
            READ_CONTACTS)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(READ_CONTACTS, WRITE_CONTACTS),
                REQUEST_CONTACTS)
        } else {
            readContacts()
        }
        //data
        val contacts = listOf<Contact>(
            Contact("Hank", "6661234"),
            Contact("Jack", "99838882"),
            Contact("Jenny", "98881234"),
            Contact("Eric", "77366363"))
        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(this)
        val adapter = object : RecyclerView.Adapter<ContactViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                    : ContactViewHolder {
                val view = layoutInflater.inflate(R.layout.contact_row, parent, false)
                return ContactViewHolder(view)
            }

            override fun getItemCount(): Int {
                return contacts.size
            }

            override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
                holder.name.setText(contacts.get(position).name)
                holder.phone.setText(contacts.get(position).phone)
            }
        }
        recycler.adapter = adapter
    }

    @SuppressLint("Range")
    private fun readContacts() {  //案例是查詢
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI, //欲查詢URI
            null,  //查詢回傳的表格欄位  null代表回傳全部欄位
            null,   //SQL語法的where
            null,  //當where中有參數時，參數放這
            null)  //ASC or DESC

        cursor?.run {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                contacts.add(Contact(name, ""))
            }
            setupRecyclerView()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQUEST_CONTACTS -> {
                if (grantResults.size > 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts()
                } else {
                    AlertDialog.Builder(this)
                        .setMessage("必須允許聯絡人權限才能顯示資料")
                        .setPositiveButton("OK", null)
                        .show()
                }
            }
        }
    }

    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.contact_name)
        val phone = view.findViewById<TextView>(R.id.contact_phone)
    }
}