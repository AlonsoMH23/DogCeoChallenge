package com.example.dogceochallenge.data.repository.source

import com.example.dogceochallenge.data.repository.source.remote.BreedsDataSource
import com.example.dogceochallenge.data.repository.source.remote.mappers.ResponseMapper
import com.example.dogceochallenge.domain.model.Breeds
import com.example.dogceochallenge.domain.repository.BreedsRepository

class BreedsDataRepository(
    private val remoteDataSource: BreedsDataSource,
    private val responseMapper: ResponseMapper
) : BreedsRepository {

    override suspend fun fetchBreeds(): Breeds {
        return responseMapper.mapFromResponseToDomain(remoteDataSource.fetchBreeds())
    }

    override suspend fun fetchBreedImagesByBreedName(breedName: String): Breeds {
        return responseMapper.mapFromResponseToDomain(
            remoteDataSource.fetchBreedImagesByBreedName(
                breedName
            )
        )
    }

}