package com.checklicenseplate.android

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_insert.*

class InsertActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        editSubmit.setOnClickListener {
            val dbHelper = MyDBHelper(this, "LicensePlate.db", 1)
            // 调用getWriteableDatabase()方法
            // 打开数据库,返回操作对象;如果不存在,则创建,即调用onCreate()方法
            val db = dbHelper.writableDatabase
            // 组装数据
            val values = ContentValues().apply {
                put("community", editCommuinty.text.toString())
                put("building", editBuilding.text.toString())
                put("room", editRoom.text.toString())
                put("owner", editOwner.text.toString())
                put("license", editLicense.text.toString())
                put("other", editOther.text.toString())
            }
            // 插入数据;第一个参数是表名,第二个用于在未指定添加数据的情况下给某些可为空的列
            // 自动赋值NULL,第三个是一个ContentValues对象,一行数据
            db.insert("LicensePlate", null, values)
            // Toast用来显示短小的信息提示,自动消失,不占屏幕空间
            // 在Activity中使用,第一个参数给this,其他类中,将this作为context传入
            Toast.makeText(this, "插入数据成功!", Toast.LENGTH_SHORT).show()
            // 主动销毁当前Activity,返回上一级;也可以由用户主动按下Back键
            finish()
        }
    }
}