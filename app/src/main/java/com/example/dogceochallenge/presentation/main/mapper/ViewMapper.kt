package com.example.dogceochallenge.presentation.main.mapper

import com.example.dogceochallenge.domain.model.Breeds
import com.example.dogceochallenge.presentation.main.models.BreedsView

object ViewMapper {
    fun fromDomainToView(breeds: Breeds): BreedsView {
        return BreedsView(
            breedsList = breeds.messages
        )
    }
}