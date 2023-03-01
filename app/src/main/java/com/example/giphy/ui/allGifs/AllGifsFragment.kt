package com.example.giphy.ui.allGifs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.giphy.R
import com.example.giphy.databinding.FragmentAllGifsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllGifsFragment : Fragment(R.layout.fragment_all_gifs) {
    private val binding by viewBinding(FragmentAllGifsBinding::bind)
    private val viewModel by viewModels<AllGifsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchButton.setOnClickListener {
                viewModel.getGifUrl(search.text.toString())
                viewModel.gif_url.observe(viewLifecycleOwner) {
                    Glide.with(requireContext()).load(it).into(gifImage)
                }
//                Glide.with(requireContext()).load(viewModel.gif_url.value).into(gifImage)
                Toast.makeText(requireContext(), viewModel.gif_url.value, Toast.LENGTH_LONG).show()
            }
        }
    }
}