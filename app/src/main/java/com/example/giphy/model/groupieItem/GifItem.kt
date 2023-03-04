package com.example.giphy.model.groupieItem

import android.view.View
import com.bumptech.glide.Glide
import com.example.giphy.R
import com.example.giphy.databinding.GifItemBinding
import com.example.giphy.model.Data
import com.example.giphy.model.Images
import com.xwray.groupie.viewbinding.BindableItem

class GifItem(val gifImageData: Images, val onGifItemClicked: (Images) -> Unit) :
    BindableItem<GifItemBinding>() {

    override fun getLayout(): Int = R.layout.gif_item

    override fun bind(viewBinding: GifItemBinding, postiion: Int) {
        viewBinding.apply {
            Glide
                .with(gifImage.context)
                .load(gifImageData.downsizedLarge.url)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(viewBinding.gifImage)
            gifImage.setOnClickListener {
                onGifItemClicked(gifImageData)
            }
        }

    }

    override fun initializeViewBinding(view: View): GifItemBinding = GifItemBinding.bind(view)

    override fun getSpanSize(spanCount: Int, position: Int): Int = 2
}