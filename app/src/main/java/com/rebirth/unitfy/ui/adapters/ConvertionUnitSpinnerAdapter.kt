package com.rebirth.unitfy.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rebirth.unitfy.databinding.ConvertionUnitSpinnerItemBinding
import com.rebirth.unitfy.domain.entities.ConvertionUnit


class ConvertionUnitSpinnerAdapter(context: Context, itemList: List<ConvertionUnit>) :
    MySpinnerAdapter<ConvertionUnit>(context, itemList) {

    override fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val convertionUnitSpinnerItemBinding: ConvertionUnitSpinnerItemBinding
        var convertViewNew = convertView

        if (convertViewNew == null) {
            val inflater = LayoutInflater.from(context)
            convertionUnitSpinnerItemBinding =
                ConvertionUnitSpinnerItemBinding.inflate(inflater, parent, false)
            convertViewNew = convertionUnitSpinnerItemBinding.root
        } else {
            convertionUnitSpinnerItemBinding =
                convertViewNew.tag as ConvertionUnitSpinnerItemBinding
        }

        convertionUnitSpinnerItemBinding.convertionUnit = getItem(position)
        convertViewNew.tag = convertionUnitSpinnerItemBinding

        return convertViewNew
    }

}