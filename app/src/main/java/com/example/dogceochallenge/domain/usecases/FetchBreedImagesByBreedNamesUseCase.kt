package com.example.dogceochallenge.domain.usecases

import com.example.dogceochallenge.domain.model.Breeds
import com.example.dogceochallenge.domain.repository.BreedsRepository

class FetchBreedImagesByBreedNamesUseCase(private val breedsRepository: BreedsRepository) {
    suspend operator fun invoke(params: Params): Breeds {
        return breedsRepository.fetchBreedImagesByBreedName(params.breedName)
    }

    data class Params(
        val breedName: String
    )
}