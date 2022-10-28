package com.rebirth.unitfy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.rebirth.unitfy.databinding.FragmentClassificationUnitSelectorBinding
import com.rebirth.unitfy.viewmodel.ClassificationUnitSelectorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassificationUnitSelectorFragment : Fragment() {

    private var _binding: FragmentClassificationUnitSelectorBinding? = null
    private val binding get() = _binding!!

    private val classificationUnitSelectorViewModel: ClassificationUnitSelectorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentClassificationUnitSelectorBinding.inflate(inflater, container, false)
        }
        binding.viewmodel = classificationUnitSelectorViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        binding.classificationRecyclerView.apply {
            val gridLayout = GridLayoutManager(this.context, 2)
            layoutManager = gridLayout
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}