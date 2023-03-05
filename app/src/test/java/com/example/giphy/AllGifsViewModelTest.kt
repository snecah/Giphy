package com.example.giphy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.example.giphy.exceptions.ScreenStateException
import com.example.giphy.model.*
import com.example.giphy.model.groupieItem.GifItem
import com.example.giphy.repository.GiphyRepository
import com.example.giphy.retrofit.GiphyApi
import com.example.giphy.ui.allGifs.AllGifsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.notNullValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AllGifsViewModelTest {
    @Mock
    private lateinit var giphyApi: GiphyApi
    private lateinit var giphyRepository: GiphyRepository
    private lateinit var viewModel: AllGifsViewModel

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        giphyRepository = GiphyRepository(giphyApi)
        viewModel = AllGifsViewModel(giphyRepository)
    }


    @Test
    fun `search with empty string should set screenState value as error`() {
        viewModel.checkStringAndGetItems("")
        assertThat(
            (viewModel.screenState.value as Result.Error).exception, instanceOf(
                ScreenStateException.EmptyInputException::class.java
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `search did not match any gifs`() = runTest() {
        val mockedResponseForNoResults = GeneralData(listOf(), null, null)

        Mockito.`when`(giphyApi.getGifs(NO_RESULTS_QUERY)).thenReturn(mockedResponseForNoResults)
        viewModel.checkStringAndGetItems(NO_RESULTS_QUERY)
        Thread.sleep(3000)
        assertThat(
            (viewModel.screenState.value as Result.Error).exception,
            instanceOf(ScreenStateException.NoResultsException::class.java)
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `check first item when searching cats`() = runTest {
        val gifImage = Images(DownsizedLarge(downsizedLargeUrl), Original(originalUrl))
        val gif = Data(gifId, gifImage, importDateTime, rating, title)

        val mockedResponseForCatGifs = GeneralData(listOf(gif), null, null)

        Mockito.`when`(giphyApi.getGifs(CATS_QUERY)).thenReturn(mockedResponseForCatGifs)
        viewModel.checkStringAndGetItems(CATS_QUERY)
        Thread.sleep(3000)
        assert(
            (viewModel.screenState.value as Result.Success).data[0].gifImageData == gifImage
        )
    }

    companion object {
        private const val NO_RESULTS_QUERY = "asdfasdgfvxz1214 32"

        private const val CATS_QUERY = "cats"


        private const val gifId = "dIVa0pwco4Mj5rQ7gy"
        private const val originalUrl =
            "https://media1.giphy.com/media/dIVa0pwco4Mj5rQ7gy/giphy.gif?cid=2724b2806ji5h8a8orlsg8uji5gquorpeyfh9zn49wol5f6o&rid=giphy.gif&ct=g"
        private const val downsizedLargeUrl =
            "https://media1.giphy.com/media/dIVa0pwco4Mj5rQ7gy/giphy.gif?cid=2724b2806ji5h8a8orlsg8uji5gquorpeyfh9zn49wol5f6o&rid=giphy.gif&ct=g"
        private const val importDateTime = "2019-02-22 21:10:53"
        private const val rating = "g"
        private const val title = "Happy International Cat Day GIF by TikTok"
    }
}