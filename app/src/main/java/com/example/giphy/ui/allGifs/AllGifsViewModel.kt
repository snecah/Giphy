package com.example.giphy.ui.allGifs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giphy.model.Data
import com.example.giphy.model.Images
import com.example.giphy.model.groupieItem.GifItem
import com.example.giphy.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.giphy.model.Result
import kotlinx.coroutines.flow.catch

@HiltViewModel
class AllGifsViewModel @Inject constructor(private val giphyRepository: GiphyRepository) :
    ViewModel() {

    private val _screenState = MutableLiveData<Result<List<GifItem>>>()
    val screenState:LiveData<Result<List<GifItem>>> = _screenState

    private var gifGroupieItems: List<GifItem> = listOf()
    var selectedGifData: Data? = null
    private var allGifsData: List<Data>? = listOf()


    val onNavigateToSelectedGifEvent = Channel<Images>()

    private fun getGifItem(keyword: String) {
        viewModelScope.launch {
            _screenState.value = Result.Loading
            giphyRepository.getGifs(keyword).catch {error ->
                _screenState.value =  Result.Error(error)
            }.collect { generalData ->
                val gifsData = generalData?.gifsData
                allGifsData = gifsData
                if (gifsData.isNullOrEmpty()) {
                    _screenState.value =
                        Result.Error(InputStringException("Your search did not match any gifs"))
                }
                else {
                    gifGroupieItems = gifsData.map {
                        GifItem(it.images, onGifItemClickedAction())
                    }
                    _screenState.value = Result.Success(gifGroupieItems)
                }
            }
        }
    }

    fun checkStringAndGetItems(inputString: String) {
        if (inputString == "")
            _screenState.value = Result.Error(InputStringException("Input must be nonempty"))
        else {
            getGifItem(inputString)
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

    private class InputStringException(message: String): Exception(message)
}