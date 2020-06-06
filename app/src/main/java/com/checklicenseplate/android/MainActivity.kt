package com.checklicenseplate.android

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MyDBHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val createLicensePlate = "create table LicensePlate (" +
            " id integer primary key autoincrement," +
            " community text not null," +
            " building text not null," +
            " room text not null," +
            " owner text," +
            " license text," +
            " other text)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createLicensePlate)
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}

class MainActivity : AppCompatActivity() {

    private val data = listOf("1","2","3","4","1","2","3","4","1","2","3","4","1","2","3","4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 数据库文件存放在 /data/data/<package>/databases目录
        val dbHelper = MyDBHelper(this, "LicensePlate.db", 1)
        // 声明鼠标点击事件
        /*createDatabase.setOnClickListener {
            // 调用getWriteableDatabase()方法
            // 打开数据库,返回操作对象;如果不存在,则创建,即调用onCreate()方法
            dbHelper.writableDatabase
        }*/

        addData.setOnClickListener {
            // 调用getWriteableDatabase()方法
            // 打开数据库,返回操作对象;如果不存在,则创建,即调用onCreate()方法
            /* val db = dbHelper.writableDatabase
             // 组装数据
             val values1 = ContentValues().apply {
                 put("name", "coda")
                 put("author", "me")
                 put("pages", 99)
                 put("price", 0)
             }
             // 插入数据;第一个参数是表名,第二个用于在未指定添加数据的情况下给某些可为空的列
             // 自动赋值NULL,第三个是一个ContentValues对象,一行数据
             db.insert("Book", null, values1)*/

            val intent = Intent(this, InsertActivity::class.java)
            startActivity(intent)
        }

        searchData.setOnClickListener {
            val db = dbHelper.writableDatabase
            // 查询方式1
            //val cursor = db.query("Book", null, null, null, null, null, null)
            // 查询方式2
            val cursor = db.rawQuery("select * from LicensePlate", null)
            var i = 0
            var list = ArrayList<String>()
            if (cursor.moveToFirst()) {
                do {
                    val community = cursor.getString(cursor.getColumnIndex("community"))
                    val building = cursor.getString(cursor.getColumnIndex("building"))
                    val room = cursor.getString(cursor.getColumnIndex("room"))
                    val owner = cursor.getString(cursor.getColumnIndex("owner"))
                    val license = cursor.getString(cursor.getColumnIndex("license"))
                    val other = cursor.getString(cursor.getColumnIndex("other"))
                    val string: String = community + " " + building + " " + room + " " + owner +
                            " " + license + " " + other
                    list.add(string)
                    i += 1
                } while (cursor.moveToNext())
            }
            cursor.close()
            // 借助适配器将数据传给ListView控件
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
            // 实际调用listView.setAdapter()方法
            listView.adapter = adapter
        }

        // 借助适配器将数据传给ListView控件
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        // 实际调用listView.setAdapter()方法
        listView.adapter = adapter
    }
}

