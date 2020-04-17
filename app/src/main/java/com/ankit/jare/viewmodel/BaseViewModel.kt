package com.ankit.jare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {

    val dataLoading = MutableLiveData<Boolean>().apply { value = false }

}