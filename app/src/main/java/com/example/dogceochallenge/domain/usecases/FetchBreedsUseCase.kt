package com.example.dogceochallenge.domain.usecases

import com.example.dogceochallenge.domain.model.Breeds
import com.example.dogceochallenge.domain.repository.BreedsRepository

class FetchBreedsUseCase(private val breedsRepository: BreedsRepository) {
    suspend operator fun invoke(): Breeds {
        return breedsRepository.fetchBreeds()
    }
}