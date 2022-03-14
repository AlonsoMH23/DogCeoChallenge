package com.example.dogceochallenge.data.repository.source.remote

import com.example.dogceochallenge.data.repository.source.remote.response.BreedsResponse

interface BreedsDataSource {
    suspend fun fetchBreeds(): BreedsResponse
    suspend fun fetchBreedImagesByBreedName(
        breedName: String
    ): BreedsResponse
}