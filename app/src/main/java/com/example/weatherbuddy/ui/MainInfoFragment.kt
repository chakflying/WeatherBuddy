package com.example.weatherbuddy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherbuddy.R
import com.example.weatherbuddy.data.HKOAPI
import kotlinx.android.synthetic.main.main_info_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainInfoFragment : Fragment() {

    companion object {
        fun newInstance() = MainInfoFragment()
    }

    private lateinit var viewModel: MainInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        val testAPI = HKOAPI()
        GlobalScope.launch(Dispatchers.Main) {
            val visibilityResponse = testAPI.getLatestVisibility()
            textView_visibility.text = visibilityResponse.data.toString()
        }
    }

}