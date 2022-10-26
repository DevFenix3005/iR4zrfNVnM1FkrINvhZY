package com.rebirth.unitfy.viewmodel

import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.rebirth.unitfy.R
import com.rebirth.unitfy.domain.entities.ConvertionUnit
import com.rebirth.unitfy.domain.entities.UnitClassification
import com.rebirth.unitfy.models.RegisterModel
import com.rebirth.unitfy.ui.dialogs.NewUnitOrClassDialog
import com.rebirth.unitfy.ui.fragments.RegisterFormulaFragment
import com.rebirth.unitfy.ui.listeners.MyOnItemSelectedListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterFormulaViewModel @Inject constructor(
    private val registerModel: RegisterModel
) : ViewModel() {

    val convertionFormula = MutableLiveData("")
    val invertionFormula = MutableLiveData("")

    var classificationUnits: MutableLiveData<List<UnitClassification>> =
        registerModel.createContentToClassificationUnits()
    var originUnits = registerModel.createEmptyConvertionUnitList()
    var destinyUnits = registerModel.createEmptyConvertionUnitList()

    private var selectedClassification = MutableLiveData<UnitClassification>(null)
    private var selectedOriginUnit = MutableLiveData<ConvertionUnit>(null)
    private var selectedDestinyUnit = MutableLiveData<ConvertionUnit>(null)

    val onClassificationItemSelectedListener = object : MyOnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            Log.i(TAG, "Seleccionada clasificacion de unidad")
            val unit: UnitClassification = classificationUnits.value?.get(position)!!
            val unitId = unit.id
            if (unitId != null && unitId != -1L) {
                selectedClassification.value = unit
                unitId.also { innerUnitId ->
                    originUnits.run {
                        value = registerModel.createConvertionUnitsByClassification(
                            innerUnitId, "Selecciona un origen"
                        )
                    }
                    destinyUnits.run {
                        value = registerModel.createConvertionUnitsByClassification(
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
                Log.i(TAG, "Seleccionada unidad de convercion de origen")
            }
        }
    }

    val onConvertionUnitDestinyItemSelectedListener = object : MyOnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val list = destinyUnits.value
            if (!list.isNullOrEmpty()) {
                selectedDestinyUnit.value = list[position]
                Log.i(TAG, "Seleccionada unidad de convercion de destino")
            }
        }
    }

    val listenerToAddClassification = object : NewUnitOrClassDialog.AddUnitOrClassListener {
        override fun onDialogPositiveClick(dialog: DialogFragment) {
            val view = dialog.dialog
            if (view != null) {
                val textInputEditText: TextInputEditText =
                    view.findViewById(R.id.new_class_or_unit_name)
                val newName = textInputEditText.editableText.toString()
                val newClassification = registerModel.createNewClassification(newName)
                Log.i(TAG, "onDialogPositiveClick: $newClassification")

                val lista = classificationUnits.value!!
                val nuevaLista = mutableListOf<UnitClassification>()
                for (element in lista) {
                    nuevaLista.add(element)
                }
                nuevaLista.add(newClassification)
                classificationUnits.value = nuevaLista
            }
        }
    }
    val listenerToAddUnit = object : NewUnitOrClassDialog.AddUnitOrClassListener {
        override fun onDialogPositiveClick(dialog: DialogFragment) {
            val view = dialog.dialog
            if (view != null) {
                val classification = selectedClassification.value
                val textInputEditTextName: TextInputEditText =
                    view.findViewById(R.id.new_class_or_unit_name)

                val textInputEditTextSufix: TextInputEditText =
                    view.findViewById(R.id.new_conversation_or_unit)


                val newName = textInputEditTextName.editableText.toString()
                val newSufix = textInputEditTextSufix.editableText.toString()
                val newConvertionUnit =
                    registerModel.createNewUnit(newName, newSufix, classification?.id!!)
                Log.i(TAG, "onDialogPositiveClick: $newConvertionUnit")
                addNewElementToList(newConvertionUnit, originUnits)
                addNewElementToList(newConvertionUnit, destinyUnits)
            }
        }
    }

    private fun addNewElementToList(
        convertioUnit: ConvertionUnit,
        list: MutableLiveData<List<ConvertionUnit>>
    ) {
        val lista = list.value!!
        val nuevaLista = mutableListOf<ConvertionUnit>()
        for (element in lista) {
            nuevaLista.add(element)
        }
        nuevaLista.add(convertioUnit)
        list.value = nuevaLista
    }


    fun onCreateDialogClassification(
        button: View,
    ) {
        val parent = button.findFragment<RegisterFormulaFragment>()
        val activity = parent.requireActivity()
        val dialog =
            NewUnitOrClassDialog("Crear nueva clasificacion", R.layout.dialog_add_new_classunit)
        dialog.listener = listenerToAddClassification
        dialog.show(activity.supportFragmentManager, "NewUnitOrClassDialog")
    }

    fun onCreateDialogConvUnit(
        button: View,
    ) {
        val parent = button.findFragment<RegisterFormulaFragment>()
        val activity = parent.requireActivity()
        val dialog = NewUnitOrClassDialog("`Crear nueva unidad", R.layout.dialog_add_new_converunit)
        dialog.listener = listenerToAddUnit
        dialog.show(activity.supportFragmentManager, "NewUnitOrClassDialog")
    }

    fun addNewMutation() {
        val convertionFormula = convertionFormula.value
        val invertionFormula = invertionFormula.value
        val selectedOriginUnit = selectedOriginUnit.value
        val selectedDestinyUnit = selectedDestinyUnit.value
        registerModel.createMutation(
            convertionFormula!!,
            invertionFormula!!,
            selectedOriginUnit?.id!!,
            selectedDestinyUnit?.id!!
        )


    }


    companion object {
        private const val TAG = "RegisterFormulaViewMode"
    }

}