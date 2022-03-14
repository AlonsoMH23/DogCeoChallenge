package com.example.dogceochallenge.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.dogceochallenge.CoroutineTestRule
import com.example.dogceochallenge.base.Result
import com.example.dogceochallenge.domain.model.Breeds
import com.example.dogceochallenge.domain.usecases.FetchBreedsUseCase
import com.example.dogceochallenge.presentation.breednames.BreedsViewModel
import com.example.dogceochallenge.presentation.main.mapper.ExceptionMapper
import com.example.dogceochallenge.presentation.main.mapper.ViewMapper
import com.example.dogceochallenge.presentation.main.models.BreedsView
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BreedsViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var fetchBreedsUseCase: FetchBreedsUseCase

    @MockK
    private lateinit var viewMapper: ViewMapper

    @MockK
    private lateinit var exceptionMapper: ExceptionMapper

    @MockK
    lateinit var itemObserver: Observer<Result<BreedsView>>

    private lateinit var viewModel: BreedsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = BreedsViewModel(
            fetchBreedsUseCase = fetchBreedsUseCase,
            viewMapper = viewMapper,
            exceptionMapper = exceptionMapper
        ).apply {
            items.observeForever(itemObserver)
        }
    }

    @After
    fun tearDown() {
        with(viewModel) {
            items.removeObserver(itemObserver)
        }
    }

    private fun verifyAll() {
        confirmVerified(
            fetchBreedsUseCase, viewMapper, exceptionMapper, itemObserver
        )
    }

    @Test
    fun `Given a initial execution When activity was created Then verify breeds data empty`() =
        coroutineTestRule.runBlockingTest {

            val breedsResult = Breeds(
                status = "success",
                messages = emptyList()
            )

            coEvery { fetchBreedsUseCase.invoke() } returns breedsResult

            viewModel.fetchBreeds()

            coVerifySequence {
                itemObserver.onChanged(Result.Loading)
                fetchBreedsUseCase.invoke()
                itemObserver.onChanged(Result.Empty)
            }
            verifyAll()
        }

    @Test
    fun `Given a initial execution When activity was created Then verify breeds data`() =
        coroutineTestRule.runBlockingTest {

            val breedsResult = Breeds(
                status = "success",
                messages = listOf("akita", "beagle", "goldentriever")
            )

            val breedsView = BreedsView(
                breedsList = listOf("akita", "beagle", "goldentriever")
            )

            coEvery { fetchBreedsUseCase.invoke() } returns breedsResult
            coEvery { viewMapper.fromDomainToView(breedsResult) } returns breedsView

            viewModel.fetchBreeds()

            coVerifySequence {
                itemObserver.onChanged(Result.Loading)
                fetchBreedsUseCase.invoke()
                viewMapper.fromDomainToView(breedsResult)
                itemObserver.onChanged(Result.Success(breedsView))
            }
            verifyAll()
        }

    @Test
    fun `Given a initial execution When activity was created Then verify error`() =
        coroutineTestRule.runBlockingTest {

            val exception = Exception("Unknown error")
            val resultError = Result.Error(exception)

            coEvery { fetchBreedsUseCase.invoke() } throws exception
            every { exceptionMapper.setErrorResult(exception) } returns resultError

            viewModel.fetchBreeds()

            coVerifySequence {
                itemObserver.onChanged(Result.Loading)
                fetchBreedsUseCase.invoke()
                exceptionMapper.setErrorResult(exception)
                itemObserver.onChanged(resultError)
            }
            verifyAll()
        }

}