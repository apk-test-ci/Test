/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.samples.apps.sunflower.adapters.GardenPlantingAdapter
import com.google.samples.apps.sunflower.databinding.FragmentGardenBinding
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModel

class GardenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentGardenBinding.inflate(inflater, container, false).let { binding ->
        val context = context ?: return binding.root
        with(GardenPlantingAdapter(context)) {
            binding.gardenList.adapter = this
            binding.setLifecycleOwner(viewLifecycleOwner)
            subscribeUi(binding, this)
        }
        binding.root
    }

    private fun subscribeUi(databinding: FragmentGardenBinding, adapter: GardenPlantingAdapter) {
        InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext()).let { factory ->
            ViewModelProviders.of(this, factory).get(GardenPlantingListViewModel::class.java)
                .let { viewModel ->
                    viewModel.gardenPlantings.observe(this, Observer { plantings ->
                        databinding.hasPlantings = (plantings != null && plantings.isNotEmpty())
                    })
                    viewModel.plantAndGardenPlantings.observe(
                        viewLifecycleOwner,
                        Observer { result ->
                            result?.let {
                                if (result.isNotEmpty())
                                    adapter.submitList(result)
                            }
                            databinding.loadingUi.visibility = View.GONE
                        })
                }
        }
    }
}
