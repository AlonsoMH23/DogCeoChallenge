package com.example.dogceochallenge.data.repository.source.remote.mappers

import com.example.dogceochallenge.data.repository.source.remote.response.BreedsResponse
import com.example.dogceochallenge.domain.model.Breeds

object ResponseMapper {

    fun mapFromResponseToDomain(breedsResponse: BreedsResponse): Breeds {
        return Breeds(
            status = breedsResponse.status,
            messages = breedsResponse.message
        )
    }
}