package com.ankit.jare.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ankit.jare.model.ListRepository
import com.ankit.jare.model.rows

class RepoListViewModel : BaseViewModel() {
    val repoListLive = MutableLiveData<List<rows>>()
    val respTitle = MutableLiveData<String>()

    fun fetchRepoList() {
        try {
            dataLoading.value = true
            ListRepository.getInstance().getRepoList { isSuccess, response ->
                dataLoading.value = false
                if (isSuccess) {
                    if (response != null) {
                        if (response.rows.size > 0) {
                            repoListLive.value = ArrayList(response.rows).apply { removeAt(7) }
                            respTitle.value = response.title
                        }
                    }
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