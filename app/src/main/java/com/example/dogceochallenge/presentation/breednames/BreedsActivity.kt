package com.example.dogceochallenge.presentation.breednames

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogceochallenge.R
import com.example.dogceochallenge.base.AppConstant
import com.example.dogceochallenge.base.AppConstant.DELAY_DURATION
import com.example.dogceochallenge.base.Result
import com.example.dogceochallenge.base.ViewModelFactory
import com.example.dogceochallenge.base.applyWithDelay
import com.example.dogceochallenge.databinding.ActivityMainBinding
import com.example.dogceochallenge.presentation.breedimages.BreedImagesActivity
import com.example.dogceochallenge.presentation.main.adapter.BreedsAdapter
import com.example.dogceochallenge.presentation.main.adapter.BreedsItemListener
import com.example.dogceochallenge.presentation.main.models.BreedsView
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class BreedsActivity : AppCompatActivity(), BreedsItemListener {

    private lateinit var binding: ActivityMainBinding

    private val viewModelFactory: ViewModelFactory by inject()
    private val viewModel by viewModels<BreedsViewModel> { viewModelFactory }

    private var recyclerViewAdapter: BreedsAdapter = BreedsAdapter(this)
    private lateinit var snackbarInfo: Snackbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerAdapter()
        initSnackBarInfo()
        manageViewModel()
    }

    private fun initSnackBarInfo() {
        snackbarInfo = Snackbar.make(binding.coordinator, "", Snackbar.LENGTH_INDEFINITE)
    }

    private fun setupRecyclerAdapter() {
        with(binding.breedsRv) {
            layoutManager = LinearLayoutManager(this@BreedsActivity)
            adapter = recyclerViewAdapter
        }
    }

    private fun manageViewModel() {
        with(viewModel) {
            items.observe(this@BreedsActivity, Observer(::renderResult))
            fetchBreeds()
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
        applyWithDelay(DELAY_DURATION) {
            binding.loadingLy.loadingPb.isGone = true
        }
    }

    private fun hideSnackbarInfo() {
        applyWithDelay(DELAY_DURATION) {
            snackbarInfo.dismiss()
        }
    }

    private fun showLoading() {
        binding.loadingLy.loadingPb.isVisible = true
    }

    private fun showSnackBar(message: String) {
        applyWithDelay(DELAY_DURATION) {
            snackbarInfo.setText(message).run {
                setAction(getString(R.string.retry)) {
                    viewModel.fetchBreeds()
                }.show()
            }
        }
    }

    private fun setAdapterData(data: BreedsView) {
        applyWithDelay(DELAY_DURATION) {
            recyclerViewAdapter.submitList(data.breedsList)
        }
    }

    override fun selectedBreed(breedName: String) {
        val intent = Intent(this, BreedImagesActivity::class.java)
        intent.putExtra(AppConstant.BREED_NAME_KEY, breedName)
        startActivity(intent)
    }

}