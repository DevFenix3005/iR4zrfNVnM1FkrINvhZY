package com.rebirth.unitfy.ui.fragments

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.rebirth.unitfy.databinding.FragmentConvertionUnitBinding
import com.rebirth.unitfy.domain.entities.ConvertionUnit
import com.rebirth.unitfy.domain.entities.UnitClassification
import com.rebirth.unitfy.ui.adapters.ConvertionUnitSpinnerAdapter
import com.rebirth.unitfy.ui.adapters.EmptySpinnerAdapter
import com.rebirth.unitfy.ui.adapters.UnitfySpinnerAdapter
import com.rebirth.unitfy.viewmodel.ConvertionUnitViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConvertionUnitFragment : Fragment() {

    private var _binding: FragmentConvertionUnitBinding? = null
    private val binding get() = _binding!!

    private val args: ConvertionUnitFragmentArgs by navArgs()
    val viewModel: ConvertionUnitViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentConvertionUnitBinding.inflate(inflater, container, false)
        }
        binding.vm = viewModel.apply {
            selectUnitsByClassification(args)
        }
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

    companion object {
        private const val TAG = "ConvertionUnitFragment"
    }
}


@BindingAdapter(value = ["entries", "itemSelected"], requireAll = true)
@Suppress("UNCHECKED_CAST")
fun AppCompatSpinner.myBindingAppCompatSpinner(
    items: List<Any>,
    listener: AdapterView.OnItemSelectedListener
) {
    val view: AppCompatSpinner = this
    view.onItemSelectedListener = listener

    if (items.isEmpty()) {
        view.adapter = EmptySpinnerAdapter(view.context)
    } else {
        val firstItem = items[0]
        when (firstItem) {
            is UnitClassification -> UnitfySpinnerAdapter(
                view.context,
                items as List<UnitClassification>
            )
            is ConvertionUnit -> ConvertionUnitSpinnerAdapter(
                view.context,
                items as List<ConvertionUnit>
            )
            else -> EmptySpinnerAdapter(view.context)
        }.also { view.adapter = it }
    }
}

@BindingAdapter("inputFocus")
fun TextInputEditText.setSoftInputFocus(flag: Boolean) {
    this.showSoftInputOnFocus = flag
}

@BindingAdapter("onFocusListener")
fun TextInputEditText.onMyFocusListener(listener: View.OnFocusChangeListener) {
    this.onFocusChangeListener = listener
}

@BindingAdapter("onTextWatcher")
fun TextInputEditText.onMyChangeListener(textWatcher: TextWatcher) {
    this.addTextChangedListener(textWatcher)
}



