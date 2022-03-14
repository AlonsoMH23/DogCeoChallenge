package com.example.dogceochallenge.presentation.breednames

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogceochallenge.base.Result
import com.example.dogceochallenge.domain.usecases.FetchBreedsUseCase
import com.example.dogceochallenge.presentation.main.mapper.ExceptionMapper
import com.example.dogceochallenge.presentation.main.mapper.ViewMapper
import com.example.dogceochallenge.presentation.main.models.BreedsView
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class BreedsViewModel(
    private val fetchBreedsUseCase: FetchBreedsUseCase,
    private val viewMapper: ViewMapper,
    private val exceptionMapper: ExceptionMapper
) : ViewModel() {

    val items: MutableLiveData<Result<BreedsView>> = MutableLiveData<Result<BreedsView>>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        items.value = exceptionMapper.setErrorResult(throwable)
    }

    fun fetchBreeds() {
        items.value = Result.Loading
        viewModelScope.launch(coroutineExceptionHandler) {
            val breedsData = fetchBreedsUseCase()
            if (breedsData.messages.isEmpty()) {
                items.value = Result.Empty
            } else {
                items.value = Result.Success(viewMapper.fromDomainToView(breedsData))
            }
        }
    }

}