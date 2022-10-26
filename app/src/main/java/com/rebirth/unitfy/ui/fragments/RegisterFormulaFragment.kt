package com.rebirth.unitfy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rebirth.unitfy.databinding.FragmentRegisterFormulaBinding
import com.rebirth.unitfy.viewmodel.RegisterFormulaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFormulaFragment : Fragment() {

    private var _binding: FragmentRegisterFormulaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterFormulaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentRegisterFormulaBinding.inflate(inflater, container, false)
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

    companion object {
        private const val TAG = "RegisterFormulaFragment"
    }


}