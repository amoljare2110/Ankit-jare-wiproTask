package com.ankit.jare.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ankit.jare.WiproApp
import com.ankit.jare.wiproDataBase.WiproEntity
import com.ankit.jare.wiproDataBase.WiproDataBase
import com.ankit.jare.model.ListRepository
import com.ankit.jare.model.ListResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RepoListViewModel : BaseViewModel() {

    val db = WiproDataBase.getDatabase(WiproApp.instance)

    var realdata = MutableLiveData<List<WiproEntity>>()

    fun fetchRepoList() {
        try {
            dataLoading.value = true
            ListRepository.getInstance().getRepoList { isSuccess, response ->
                dataLoading.value = false
                if (isSuccess) {
                    if (response != null && response.rows.size > 0) {
                        saveAndGetData(response)
                    }
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveAndGetData(response: ListResponse) {
        try {
            CoroutineScope(IO).launch {
                val wiproEntity = WiproEntity("title", "description", "imageHref", "headerTitle")
                if (response != null) {

                    for (i in response.rows.indices) {
                        if (i != 7 && i != 10) {
                            if (response.rows[i].title == null) {
                                wiproEntity.title = " "
                            } else {
                                wiproEntity.title = response.rows[i].title
                            }

                            if (response.rows[i].description == null) {
                                wiproEntity.description = " "
                            } else {
                                wiproEntity.description = response.rows[i].description
                            }

                            if (response.rows[i].imageHref == null) {
                                wiproEntity.imageHref = " "
                            } else {
                                wiproEntity.imageHref = response.rows[i].imageHref
                            }
                            wiproEntity.headerTitle = response.title

                            db.wiproDao().saveData(wiproEntity)
                        }
                    }
                    realdata.postValue(db.wiproDao().getRecords())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}