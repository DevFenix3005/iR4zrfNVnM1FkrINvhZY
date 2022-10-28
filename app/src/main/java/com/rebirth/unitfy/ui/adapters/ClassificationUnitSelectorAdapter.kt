package com.rebirth.unitfy.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rebirth.unitfy.BR
import com.rebirth.unitfy.databinding.ClassificationItemBinding
import com.rebirth.unitfy.domain.entities.UnitClassification
import com.rebirth.unitfy.viewmodel.ClassificationItemViewModel

class ClassificationUnitSelectorAdapter(private val classificationUnitList: List<UnitClassification>) :
    RecyclerView.Adapter<ClassificationUnitSelectorAdapter.ViewHolder>() {

    class ViewHolder(private val classificationItemBinding: ClassificationItemBinding) :
        RecyclerView.ViewHolder(classificationItemBinding.root) {

        fun bind(obj: ClassificationItemViewModel) {
            classificationItemBinding.setVariable(BR.classificationUnit, obj)
            classificationItemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val classBinding = ClassificationItemBinding.inflate(inflater, parent, false)
        return ViewHolder(classBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj = classificationUnitList[position]
        val viewModel = ClassificationItemViewModel(obj)
        holder.bind(viewModel)
    }

    override fun getItemCount(): Int = classificationUnitList.size

}