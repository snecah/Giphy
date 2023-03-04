package com.example.giphy.exceptions

sealed class ScreenStateException() : Exception() {
    class NoResultsException() : ScreenStateException()
    class EmptyInputException() : ScreenStateException()
}