package com.example.dogceochallenge.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dogceochallenge.domain.repository.BreedsRepository
import com.example.dogceochallenge.domain.usecases.FetchBreedImagesByBreedNamesUseCase
import com.example.dogceochallenge.domain.usecases.FetchBreedsUseCase
import com.example.dogceochallenge.presentation.breedimages.BreedsImagesViewModel
import com.example.dogceochallenge.presentation.breednames.BreedsViewModel
import com.example.dogceochallenge.presentation.main.mapper.ExceptionMapper
import com.example.dogceochallenge.presentation.main.mapper.ViewMapper

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val breedsRepository: BreedsRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(BreedsViewModel::class.java) ->
                    BreedsViewModel(
                        fetchBreedsUseCase = FetchBreedsUseCase(breedsRepository),
                        viewMapper = ViewMapper,
                        exceptionMapper = ExceptionMapper
                    )
                isAssignableFrom(BreedsImagesViewModel::class.java) ->
                    BreedsImagesViewModel(
                        fetchBreedImagesByBreedNamesUseCase = FetchBreedImagesByBreedNamesUseCase(
                            breedsRepository
                        ),
                        viewMapper = ViewMapper,
                        exceptionMapper = ExceptionMapper
                    )
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

}

