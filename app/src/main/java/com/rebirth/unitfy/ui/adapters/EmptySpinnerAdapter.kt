package com.rebirth.unitfy.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rebirth.unitfy.R

class EmptySpinnerAdapter(context: Context) :
    MySpinnerAdapter<String>(context, listOf("String")) {


    override fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(R.layout.empty_spinner, parent, false)
    }


}