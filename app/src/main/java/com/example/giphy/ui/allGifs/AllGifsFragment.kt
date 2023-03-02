package com.example.giphy.ui.allGifs

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.giphy.R
import com.example.giphy.databinding.FragmentAllGifsBinding
import com.example.giphy.model.gropieItem.GifItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllGifsFragment : Fragment(R.layout.fragment_all_gifs) {
    private val binding by viewBinding(FragmentAllGifsBinding::bind)
    private val viewModel by viewModels<AllGifsViewModel>()
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val layoutManager = GridLayoutManager(this.context, 5, GridLayoutManager.VERTICAL, false)

    private val onItemClickListener = OnItemClickListener { item, view ->
        if (item is GifItem) {
            Toast.makeText(requireContext(), item.gif.downsized_large.url, Toast.LENGTH_LONG).show()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager
            searchButton.setOnClickListener {
                viewModel.checkStringAndGetItems(search.text.toString())
                searchButton.visibility = View.GONE;
                val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
            }
            search.setOnClickListener {
                searchButton.visibility = View.VISIBLE
                val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(requireView(), 0)
            }

            search.addTextChangedListener {
                searchButton.visibility = View.VISIBLE
            }
        }

        viewModel.isStringEmpty.observe(viewLifecycleOwner) {
            if (it == true)
                Toast.makeText(requireContext(), "Input must be nonempty", Toast.LENGTH_LONG).show()
        }

        viewModel.gifItems.observe(viewLifecycleOwner) {
            adapter.update(it)
        }
        adapter.setOnItemClickListener(onItemClickListener)
    }



}
