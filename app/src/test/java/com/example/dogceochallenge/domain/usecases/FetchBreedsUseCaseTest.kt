package com.example.dogceochallenge.domain.usecases

import com.example.dogceochallenge.CoroutineTestRule
import com.example.dogceochallenge.domain.model.Breeds
import com.example.dogceochallenge.domain.repository.BreedsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchBreedsUseCaseTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @MockK
    private lateinit var breedsRepository: BreedsRepository

    private lateinit var fetchBreedsUseCase: FetchBreedsUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        fetchBreedsUseCase = FetchBreedsUseCase(breedsRepository = breedsRepository)
    }

    private fun verifyAll() {
        confirmVerified(breedsRepository)
    }

    @Test
    fun `Given a useCase execution When activity was created Then verify fetch breeds names call`() =
        coroutineTestRule.runBlockingTest {

            coEvery { breedsRepository.fetchBreeds() } returns Breeds(
                status = "success",
                messages = emptyList()
            )

            val result = fetchBreedsUseCase.invoke()

            assertEquals("success", result.status)
            assertTrue(result.messages.isEmpty())

            coVerifySequence {
                breedsRepository.fetchBreeds()
            }
            verifyAll()
        }

}