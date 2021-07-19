package com.weatherflow.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.weatherflow.R
import com.weatherflow.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel by viewModel<HomeFragmentViewModel>()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewModel.viewState
                .flowWithLifecycle(lifecycle)
                .collect(::updateState)
        }

        return binding.root
    }

    private fun updateState(state: HomeViewState) = with(binding) {
        when (state) {
            is HomeViewState.Loading -> {
                homeProgress.isVisible = true
                homeError.isVisible = false
                homeCard.isVisible = false
            }
            is HomeViewState.Content -> {
                homeProgress.isVisible = false
                homeError.isVisible = false
                homeCard.isVisible = true
                locationName.text = state.locationName
                temp.text = state.temp
                tempMinMax.text = state.tempMinMax
                dateTime.text = state.dateTime
                description.text = state.description
            }
            is HomeViewState.Error -> {
                homeProgress.isVisible = false
                homeError.isVisible = true
                homeCard.isVisible = false
                homeError.text = state.message
            }
        }
    }
}