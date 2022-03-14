package com.example.dogceochallenge.data.repository.source.remote

import com.example.dogceochallenge.data.repository.source.remote.api.ApiService
import com.example.dogceochallenge.data.repository.source.remote.response.BreedsResponse

class BreedsApiDataSource(private val apiService: ApiService) : BreedsDataSource {
    override suspend fun fetchBreeds(): BreedsResponse {
        return apiService.fetchBreeds()
    }

    override suspend fun fetchBreedImagesByBreedName(breedName: String): BreedsResponse {
        return apiService.fetchBreedImagesByBreedName(breedName)
    }
}