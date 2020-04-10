package com.ankit.jare.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ankit.jare.model.ListRepository
import com.ankit.jare.model.ListResponse
import com.ankit.jare.model.rows

class RepoListViewModel : BaseViewModel() {
    val repoListLive = MutableLiveData<List<rows>>()

    fun fetchRepoList() {
        try {
            dataLoading.value = true
            ListRepository.getInstance().getRepoList { isSuccess, response ->
                dataLoading.value = false
                if (isSuccess) {
                    repoListLive.value = response?.rows
                    empty.value = false
                } else {
                    empty.value = true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}