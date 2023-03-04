package com.example.giphy.ui.allGifs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giphy.model.Data
import com.example.giphy.model.GeneralData
import com.example.giphy.model.Images
import com.example.giphy.model.groupieItem.GifItem
import com.example.giphy.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllGifsViewModel @Inject constructor(private val giphyRepository: GiphyRepository) :
    ViewModel() {

    //оберни LiveData в Result по аналогии с YouTrader
    private val _isStringEmpty = MutableLiveData<Boolean>()
    val isStringEmpty: LiveData<Boolean>
        get() = _isStringEmpty

    private val _isDataEmpty = MutableLiveData<Boolean>()
    val isDataEmpty: LiveData<Boolean>
        get() = _isDataEmpty

    var selectedGifData: Data? = null

    private var allGifsData: List<Data>? = listOf()

    private val _gifItems = MutableLiveData<List<GifItem>>()
    val gifItems: LiveData<List<GifItem>>
        get() = _gifItems

    val onNavigateToSelectedGifEvent = Channel<Images>()

    private fun getGifItems(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val gifsData = giphyRepository.getGifs(keyword).body()?.data
            if (gifsData.isNullOrEmpty())
                _isDataEmpty.postValue(true)
            else {
                _isDataEmpty.postValue(false)
                allGifsData = gifsData
                _gifItems.postValue(
                    gifsData.map {
                        GifItem(
                            it.images,
                            onGifItemClickedAction()
                        )
                    })
            }
        }
    }

    fun checkStringAndGetItems(inputString: String) {
        if (inputString == "")
            _isStringEmpty.value = true
        else {
            getGifItems(inputString)
        }
    }

    private fun onGifItemClickedAction(): (Images) -> Unit = { selectedImage ->
        selectedGifData = findDataByGif(selectedImage)
        viewModelScope.launch {
            onNavigateToSelectedGifEvent.send(selectedImage)
        }
    }

    private fun findDataByGif(selectedImage: Images): Data? {
        allGifsData?.forEach {
            if (it.images == selectedImage)
                return it
        }
        return null
    }
}