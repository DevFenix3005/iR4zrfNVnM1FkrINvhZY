package com.rebirth.unitfy.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

abstract class MySpinnerAdapter<T>(context: Context, list: List<T>) :
    ArrayAdapter<T>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return this.initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return this.initView(position, convertView, parent)
    }

    override fun isEnabled(position: Int): Boolean = position != 0

    protected abstract fun initView(position: Int, convertView: View?, parent: ViewGroup): View

}