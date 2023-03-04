package com.example.giphy.ui.allGifs

import android.os.Bundle
import android.util.Log
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
import com.example.giphy.model.Result
import kotlinx.android.synthetic.main.fragment_all_gifs.*


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

        viewModel.screenState.observe(viewLifecycleOwner) {screenState ->
            when(screenState) {
                is Result.Success -> {
                    adapter.update(screenState.data)
                    binding.recyclerView.visibility = View.VISIBLE
                }
                is Result.Loading -> {
                    binding.recyclerView.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), screenState.exception.message, Toast.LENGTH_LONG).show()
                    adapter.clear()
                    binding.recyclerView.visibility = View.GONE
                    screenState.exception.message?.let { Log.d(TAG, it) }
            }
            }
        }


        //сделай, чтобы из ViewModel торчала одна LiveData, а не несколько, подписывайся на нее(ScreenState) и в зависимости от него отрисовывай UI
//        viewModel.isStringEmpty.observe(viewLifecycleOwner) {
//            if (it == true)
//                Toast.makeText(requireContext(), "Input must be nonempty", Toast.LENGTH_LONG).show()
//        }
//
//        viewModel.gifItems.observe(viewLifecycleOwner) {
//            adapter.update(it)
//        }
//
//        viewModel.isDataEmpty.observe(viewLifecycleOwner) {
//            if (it == true)
//                Toast.makeText(requireContext(), "Your search \"${binding.searchEditText.text}\" did not match any GIFs", Toast.LENGTH_LONG)
//                    .show()
//        }
    }

    private fun onPerformSearchAction(searchQuery: String, actionId: Int) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            viewModel.checkStringAndGetItems(searchQuery)
        }
    }
    companion object {
        private const val TAG = "AllGifsFragment"
    }
}