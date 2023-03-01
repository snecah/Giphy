package com.example.giphy.ui.allGifs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giphy.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllGifsViewModel @Inject constructor(private val giphyRepository: GiphyRepository): ViewModel(){

    private val _gif_url = MutableLiveData<String>("nothing")
    val gif_url: LiveData<String>
        get() = _gif_url

    fun getGifUrl(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _gif_url.postValue(giphyRepository.getGifs(keyword).body()!!.data[0].images.downsized_large.url)
        }
    }
}