package com.example.giphy.ui.allGifs

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.giphy.R
import com.example.giphy.databinding.FragmentAllGifsBinding
import com.example.giphy.model.groupieItem.GifItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.gif_item.*


@AndroidEntryPoint
class AllGifsFragment : Fragment(R.layout.fragment_all_gifs) {
    private val binding by viewBinding(FragmentAllGifsBinding::bind)
    private val viewModel by viewModels<AllGifsViewModel>()
    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }
   // private val layoutManager = GridLayoutManager(this.context, 5, GridLayoutManager.VERTICAL, false) //5 вынеси в константу с понятны названием

    // обработку нажатий делай внутри viewHolder'a. То есть сам listener будет внутри GifItem'a, в GifItem нужно будет передать лямбду и обрабатывать ее в AllGifsFragment. Я тебе покажу крч
    private val onItemClickListener = OnItemClickListener { item, view ->
        if (item is GifItem) {
            viewModel.displayGifDetails(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {
            recyclerView.adapter = adapter
            search.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.checkStringAndGetItems(search.text.toString())
                }
                true
            }
        }

        adapter.setOnItemClickListener(onItemClickListener)

        //сделай, чтобы из ViewModel торчала одна LiveData, а не несколько, подписывайся на нее(ScreenState) и в зависимости от него отрисовывай UI
        viewModel.isStringEmpty.observe(viewLifecycleOwner) {
            if (it == true)
                Toast.makeText(requireContext(), "Input must be nonempty", Toast.LENGTH_LONG).show()
        }

        viewModel.gifItems.observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        viewModel.navigateToSelectedPGif.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                view.findNavController().navigate(AllGifsFragmentDirections.actionAllGifsFragmentToGifDetailedFragment(it.gif))
                viewModel.displayGifDetailsComplete()
            }
        })
    }
}
