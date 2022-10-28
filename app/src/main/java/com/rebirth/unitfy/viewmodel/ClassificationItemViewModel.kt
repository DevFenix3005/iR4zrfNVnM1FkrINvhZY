package com.rebirth.unitfy.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.rebirth.unitfy.domain.entities.UnitClassification
import com.rebirth.unitfy.ui.fragments.ClassificationUnitSelectorFragmentDirections as Direction

class ClassificationItemViewModel(
    val payload: UnitClassification
) : ViewModel() {

    fun goToDestinyById(view: View) {
        Log.i(TAG, "goToDestinyById: $payload")
        val action =
            Direction.actionClassificationUnitSelectorFragmentToConvertionUnitFragment(payload.id!!)
        view.findNavController().navigate(action)
    }


    companion object {
        private const val TAG = "ClassificationItemViewM"
    }
}