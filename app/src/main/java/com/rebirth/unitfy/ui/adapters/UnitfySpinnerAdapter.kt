package com.rebirth.unitfy.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rebirth.unitfy.databinding.ClassunitSpinnerItemBinding
import com.rebirth.unitfy.domain.entities.UnitClassification


class UnitfySpinnerAdapter(context: Context, itemList: List<UnitClassification>) :
    MySpinnerAdapter<UnitClassification>(context, itemList) {


    override fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val classunitSpinnerItemBinding: ClassunitSpinnerItemBinding
        var convertViewNew = convertView

        if (convertViewNew == null) {
            val inflater = LayoutInflater.from(context)
            classunitSpinnerItemBinding =
                ClassunitSpinnerItemBinding.inflate(inflater, parent, false)
            convertViewNew = classunitSpinnerItemBinding.root
        } else {
            classunitSpinnerItemBinding = convertViewNew.tag as ClassunitSpinnerItemBinding
        }

        classunitSpinnerItemBinding.unitClass = getItem(position)
        convertViewNew.tag = classunitSpinnerItemBinding

        return convertViewNew
    }


}