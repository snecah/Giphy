package com.example.giphy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.giphy.exceptions.ScreenStateException
import com.example.giphy.model.Result
import com.example.giphy.model.groupieItem.GifItem
import com.example.giphy.repository.GiphyRepository
import com.example.giphy.ui.allGifs.AllGifsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AllGifsViewModelTest {
    @Mock
    private lateinit var giphyRepository: GiphyRepository
    private lateinit var viewModel: AllGifsViewModel

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = AllGifsViewModel(giphyRepository)
    }

    @Test
    fun `search with empty string should set screenState value as error`() {
        viewModel.checkStringAndGetItems("")
        viewModel.screenState.value?.equals(Result.Error(ScreenStateException.EmptyInputException()))
            ?.let { assert(it) }
    }
}