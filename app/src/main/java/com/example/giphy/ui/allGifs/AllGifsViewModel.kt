package com.example.giphy.ui.allGifs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giphy.model.gropieItem.GifItem
import com.example.giphy.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllGifsViewModel @Inject constructor(private val giphyRepository: GiphyRepository): ViewModel(){

    private val _isStringEmpty = MutableLiveData<Boolean>()
    val isStringEmpty: LiveData<Boolean>
        get() = _isStringEmpty

    private val _gifItems = MutableLiveData<List<GifItem>>()
    val gifItems: LiveData<List<GifItem>>
        get() = _gifItems

    private fun getGifItems(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _gifItems.postValue(giphyRepository.getGifs(keyword).body()!!.data.map { GifItem(it.images) })
        }
    }

    fun checkStringAndGetItems(inputString: String) {
        if (inputString == "")
            _isStringEmpty.value = true
        else {
            getGifItems(inputString)
        }
    }
}