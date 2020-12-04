package com.example.bmi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class UnitSpinner(
        context: Context,infoList:List<Unit>
):ArrayAdapter<Unit>(context,0,infoList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {

        val sem = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.unit_spinner, parent, false)
        view.findViewById<TextView>(R.id.sem_name).text = sem!!.Unit
        return view
    }
}
