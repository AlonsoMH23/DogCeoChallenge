package com.example.dogceochallenge.presentation.breedimages

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogceochallenge.R
import com.example.dogceochallenge.base.AppConstant
import com.example.dogceochallenge.base.AppConstant.BREED_NAME_KEY
import com.example.dogceochallenge.base.Result
import com.example.dogceochallenge.base.ViewModelFactory
import com.example.dogceochallenge.base.applyWithDelay
import com.example.dogceochallenge.databinding.ActivityBreedImagesBinding
import com.example.dogceochallenge.presentation.breedimages.adapter.BreedImagesAdapter
import com.example.dogceochallenge.presentation.main.models.BreedsView
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class BreedImagesActivity : AppCompatActivity() {

    private lateinit var breedName: String
    private var imageRecyclerViewAdapter: BreedImagesAdapter = BreedImagesAdapter()
    private lateinit var binding: ActivityBreedImagesBinding
    private lateinit var snackbarInfo: Snackbar
    private val viewModelFactory: ViewModelFactory by inject()
    private val viewModel by viewModels<BreedsImagesViewModel> { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreedImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBreedName()
        setupRecyclerAdapter()
        initSnackBarInfo()
        manageViewModel()
    }

    private fun getBreedName() {
        val intent = intent
        breedName = intent.getStringExtra(BREED_NAME_KEY) ?: ""
    }

    private fun initSnackBarInfo() {
        snackbarInfo = Snackbar.make(binding.root, "", Snackbar.LENGTH_INDEFINITE)
    }

    private fun setupRecyclerAdapter() {
        with(binding.breedImagesRv) {
            layoutManager = LinearLayoutManager(this@BreedImagesActivity)
            adapter = imageRecyclerViewAdapter
        }
    }

    private fun manageViewModel() {
        with(viewModel) {
            items.observe(this@BreedImagesActivity, Observer(::renderResult))
            viewModel.fetchBreedImagesByBreed(breedName)
        }
    }

    private fun renderResult(result: Result<BreedsView>?) {
        when (result) {
            is Result.Success -> {
                hideLoading()
                hideSnackbarInfo()
                setAdapterData(result.data)
            }
            is Result.Error -> {
                hideLoading()
                showSnackBar(getString(R.string.connection_error))
            }
            Result.Loading -> {
                showLoading()
            }
            Result.Empty -> {
                hideLoading()
                showSnackBar(getString(R.string.empty_data_list))
            }
        }
    }

    private fun hideLoading() {
        applyWithDelay(AppConstant.DELAY_DURATION) {
            binding.loadingLy.loadingPb.isGone = true
        }
    }

    private fun hideSnackbarInfo() {
        applyWithDelay(AppConstant.DELAY_DURATION) {
            snackbarInfo.dismiss()
        }
    }

    private fun showLoading() {
        binding.loadingLy.loadingPb.isVisible = true
    }

    private fun showSnackBar(message: String) {
        applyWithDelay(AppConstant.DELAY_DURATION) {
            snackbarInfo.setText(message).run {
                setAction(getString(R.string.retry)) {
                    viewModel.fetchBreedImagesByBreed(breedName)
                }.show()
            }
        }
    }

    private fun setAdapterData(data: BreedsView) {
        applyWithDelay(AppConstant.DELAY_DURATION) {
            imageRecyclerViewAdapter.submitList(data.breedsList)
        }
    }
}