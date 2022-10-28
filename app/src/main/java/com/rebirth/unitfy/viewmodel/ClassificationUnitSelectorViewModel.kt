package com.rebirth.unitfy.viewmodel

import androidx.lifecycle.ViewModel
import com.rebirth.unitfy.models.ClassificationUnitSelectorModel
import com.rebirth.unitfy.ui.adapters.ClassificationUnitSelectorAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClassificationUnitSelectorViewModel @Inject constructor(
    private val model: ClassificationUnitSelectorModel
) : ViewModel() {

    val classViewAdapter: ClassificationUnitSelectorAdapter
        get() {
            val classUnitList = model.getClassification()
            val adapter = ClassificationUnitSelectorAdapter(classUnitList)
            return adapter
        }


    companion object {
        private const val TAG = "ClassificationUnitSelec"
    }

}