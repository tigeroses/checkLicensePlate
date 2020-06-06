package com.checklicenseplate.android

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ItemAdapter(activity: Activity, val resourceId: Int, data : List<Item>):
    ArrayAdapter<Item>(activity, resourceId, data){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(resourceId, parent,false)
        val name: TextView = view.findViewById(R.id.itemName)
        val item = getItem(position)
        if (item != null) {
            name.text = item.name
        }
        return view
    }
}