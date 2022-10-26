package com.rebirth.unitfy.viewmodel

import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.rebirth.unitfy.R
import com.rebirth.unitfy.domain.entities.ConvertionUnit
import com.rebirth.unitfy.domain.entities.Mutation
import com.rebirth.unitfy.domain.entities.UnitClassification
import com.rebirth.unitfy.models.ConvertionModel
import com.rebirth.unitfy.ui.listeners.MyOnItemSelectedListener
import com.rebirth.unitfy.ui.listeners.MyTextWatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConvertionUnitViewModel @Inject constructor(
    private val convertionModel: ConvertionModel
) : ViewModel() {

    val classificationUnits: MutableLiveData<List<UnitClassification>>
        get() = convertionModel.createContentToClassificationUnits()

    val originQ = MutableLiveData("")
    val destinyQ = MutableLiveData("")
    val inputSelected = MutableLiveData(0)

    var originUnits = convertionModel.createEmptyConvertionUnitList()
    var destinyUnits = convertionModel.createEmptyConvertionUnitList()
    private var selectedOriginUnit = MutableLiveData<ConvertionUnit>(null)
    private var selectedDestinyUnit = MutableLiveData<ConvertionUnit>(null)
    private var selectedMutation = MutableLiveData<Mutation>(null)

    val onClassificationItemSelectedListener = object : MyOnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            Log.i(TAG, "Seleccionada clasificacion de unidad")
            val unit: UnitClassification = classificationUnits.value?.get(position)!!
            val unitId = unit.id
            if (unitId != null && unitId != -1L) {
                unitId.also { innerUnitId ->
                    originUnits.run {
                        value = convertionModel.createConvertionUnitsByClassification(
                            innerUnitId, "Selecciona un origen"
                        )
                    }
                    destinyUnits.run {
                        value = convertionModel.createConvertionUnitsByClassification(
                            innerUnitId, "Selecciona un destino"
                        )
                    }
                }
            }
        }
    }

    val onConvertionUnitOriginItemSelectedListener = object : MyOnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val list = originUnits.value
            if (!list.isNullOrEmpty()) {
                selectedOriginUnit.value = list[position]
                selectMutation()
                convertionFunction()
                Log.i(TAG, "Seleccionada unidad de convercion de origen")
            }
        }
    }

    val onConvertionUnitDestinyItemSelectedListener = object : MyOnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val list = destinyUnits.value
            if (!list.isNullOrEmpty()) {
                selectedDestinyUnit.value = list[position]
                selectMutation()
                invertConvertionFunction()
                Log.i(TAG, "Seleccionada unidad de convercion de destino")
            }
        }
    }

    val onFocusListener: View.OnFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
        (view as TextInputEditText).also { input ->
            val whoInput = if (input.id == R.id.from_unit_convertion_input) 1 else 2
            if (hasFocus) {
                inputSelected.value = whoInput
                Log.i("ConvertionUnitViewModel", "input seleccionado = ${this.inputSelected.value}")
            }
        }
    }

    val onInputTextWatcher = object : MyTextWatcher {
        override fun afterTextChanged(editText: Editable?) {
            Log.i(TAG, "Valor actual ${editText.toString()}")
            when (inputSelected.value) {
                1 -> convertionFunction()
                2 -> invertConvertionFunction()
            }
        }
    }

    private fun selectMutation() {
        val convertionUnitOrigin = this.selectedOriginUnit.value
        val convertionUnitDestiny = this.selectedDestinyUnit.value
        if (convertionUnitOrigin != null && convertionUnitDestiny != null) {
            val cuoId = convertionUnitOrigin.id.takeIf { it != -1L }
            val cudId = convertionUnitDestiny.id.takeIf { it != -1L }
            if (cuoId != null && cudId != null) {
                selectedMutation.value = convertionModel.fetchMutationByUnits(cuoId, cudId)
            }
        }
    }

    private fun convertionFunction() {
        val mutationWithUnits = selectedMutation.value
        if (mutationWithUnits != null) {
            val formula = mutationWithUnits.formulaConvertion
            this.destinyQ.value = this.originQ.value.let { origin ->
                return@let convertionModel.solveFormula(origin!!, formula)
            }
        }
    }

    private fun invertConvertionFunction() {
        val mutationWithUnits = selectedMutation.value
        if (mutationWithUnits != null) {
            val formula = mutationWithUnits.formulaInvertion
            this.originQ.value = this.destinyQ.value.let { destiny ->
                return@let convertionModel.solveFormula(destiny!!, formula)
            }
        }
    }

    fun onClickButtonPanel(key: String) {
        Log.i("ConvertionUnitViewModel", "Button clicked = $key")
        val seletecdInput: MutableLiveData<String>? = when (inputSelected.value) {
            1 -> originQ
            2 -> destinyQ
            else -> null
        }
        if (seletecdInput != null) {
            val currentValue = seletecdInput.value
            seletecdInput.value = convertionModel.tapButtonHandler(key, currentValue!!)
        }
    }

    override fun onCleared() {
        super.onCleared()
        originQ.value = ""
        destinyQ.value = ""
        inputSelected.value = 0

        originUnits = convertionModel.createEmptyConvertionUnitList()
        destinyUnits = convertionModel.createEmptyConvertionUnitList()
        selectedOriginUnit = MutableLiveData<ConvertionUnit>(null)
        selectedDestinyUnit = MutableLiveData<ConvertionUnit>(null)
        selectedMutation = MutableLiveData<Mutation>(null)
    }

    companion object {
        private const val TAG = "ConvertionUnitViewModel"
    }


}