package com.example.atm

//JAVA
//class SomeTask() : AsyncTask<Void, Void, String>() {
//    override fun doInBackground(vararg params: Void?): String? {
//        //連線網路等耗時工作
//    }
//    override fun onPreExecute() {
//        super.onPreExecute()
//        //耗時工作前可更新 UI 元件
//    }
//
//    override fun onPostExecute(result: String?) {
//        super.onPostExecute(result)
//        //耗時工作完成時，可將資料更新至UI元件
//    }
//
//
//}
//Kotlin的ANKO
//
//doAsync{
//    //連線網路等耗時工作
//    uiThread{
//        //耗時工作完成時，可將資料更新至UI元件
//    }
//
//}