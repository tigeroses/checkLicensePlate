package com.checklicenseplate.android

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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

    /*private val data = listOf("1","2","3","4","1","2","3","4","1","2","3","4","1","2","3","4")*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 数据库文件存放在 /data/data/<package>/databases目录
        val dbHelper = MyDBHelper(this, "LicensePlate.db", 1)

        searchData.setOnClickListener {
            val db = dbHelper.writableDatabase
            // 查询方式1
            //val cursor = db.query("Book", null, null, null, null, null, null)
            // 查询方式2
            var inputText = editInput.text.toString()
            var cursor = db.rawQuery("select * from LicensePlate", null)
            if (inputText.isNotEmpty()) {
                cursor = db.rawQuery("select * from LicensePlate where License like '%$inputText%'", null)
            }




            var list = ArrayList<Item>()
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
                    list.add(Item(string))
                } while (cursor.moveToNext())
            }
            cursor.close()
            // 借助适配器将数据传给ListView控件
            val adapter = ItemAdapter(this, R.layout.result_item, list)
            // 实际调用listView.setAdapter()方法
            listView.adapter = adapter
        }
    }

    // 给当前Activity创建菜单
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    // 定义响应菜单事件
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_item ->
                startActivity(Intent(this, InsertActivity::class.java))
            R.id.remove_item ->
                Toast.makeText(this, "删除功能暂未开放", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}

