package com.example.giphy.ui.allGifs

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.giphy.R
import com.example.giphy.databinding.FragmentAllGifsBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.reflect.full.memberProperties


@AndroidEntryPoint
class AllGifsFragment : Fragment(R.layout.fragment_all_gifs) {

    private val binding by viewBinding(FragmentAllGifsBinding::bind)
    private val viewModel by viewModels<AllGifsViewModel>()
    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter
            searchEditText.setOnEditorActionListener { _, actionId, _ ->
                onPerformSearchAction(searchEditText.text.toString(), actionId)
                true
            }
        }

        lifecycleScope.launch {
            val imageToNavigateInfo = viewModel.onNavigateToSelectedGifEvent.receive()
            findNavController().navigate(
                AllGifsFragmentDirections.actionAllGifsFragmentToGifDetailedFragment(
                    imageToNavigateInfo, viewModel.selectedGifData
                )
            )
        }

        //сделай, чтобы из ViewModel торчала одна LiveData, а не несколько, подписывайся на нее(ScreenState) и в зависимости от него отрисовывай UI
        viewModel.isStringEmpty.observe(viewLifecycleOwner) {
            if (it == true)
                Toast.makeText(requireContext(), "Input must be nonempty", Toast.LENGTH_LONG).show()
        }

        viewModel.gifItems.observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        viewModel.isDataEmpty.observe(viewLifecycleOwner) {
            if (it == true)
                Toast.makeText(requireContext(), "Your search \"${binding.searchEditText.text}\" did not match any GIFs", Toast.LENGTH_LONG)
                    .show()
        }
    }

    private fun onPerformSearchAction(searchQuery: String, actionId: Int) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            viewModel.checkStringAndGetItems(searchQuery)
        }
    }
}
