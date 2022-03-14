package com.example.dogceochallenge.domain.repository

import com.example.dogceochallenge.domain.model.Breeds

interface BreedsRepository {
    suspend fun fetchBreeds(): Breeds
    suspend fun fetchBreedImagesByBreedName(
        breedName: String
    ): Breeds
}