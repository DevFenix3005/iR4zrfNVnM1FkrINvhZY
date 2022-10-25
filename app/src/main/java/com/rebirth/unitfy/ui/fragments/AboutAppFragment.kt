package com.rebirth.unitfy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rebirth.unitfy.R
import com.rebirth.unitfy.viewmodel.AboutAppViewModel

class AboutAppFragment : Fragment() {

    private lateinit var viewModel: AboutAppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_app, container, false)
    }


}