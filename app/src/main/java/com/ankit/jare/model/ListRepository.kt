package com.ankit.jare.model

import com.ankit.jare.model.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException
import javax.inject.Inject

class ListRepository @Inject constructor() {

    // GET item list from API
    fun getRepoList(onResult: (isSuccess: Boolean, response: ListResponse?) -> Unit) {
        try {
            ApiClient.instance.getList().enqueue(object : Callback<ListResponse> {
                override fun onResponse(call: Call<ListResponse>?, response: Response<ListResponse>?) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<ListResponse>?, t: Throwable?) {
                    onResult(false, null)
                }

            })
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    companion object {
        private var INSTANCE: ListRepository? = null
        fun getInstance() = INSTANCE
                ?: ListRepository().also {
                    INSTANCE = it
                }
    }
}