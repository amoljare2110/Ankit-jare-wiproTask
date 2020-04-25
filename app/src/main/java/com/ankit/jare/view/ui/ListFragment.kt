package com.ankit.jare.view.ui

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ankit.jare.R
import com.ankit.jare.WiproApp
import com.ankit.jare.databinding.FragmentRepoListBinding
import com.ankit.jare.wiproDataBase.WiproDataBase
import com.ankit.jare.view.adapter.ListAdapter
import com.ankit.jare.utils.NetworkConnecitity
import com.ankit.jare.viewmodel.BaseViewModel
import com.ankit.jare.viewmodel.RepoListViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.fragment_repo_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject

class ListFragment : Fragment() {

    @Inject
    lateinit var viewModel: BaseViewModel

    private lateinit var viewDataBinding: FragmentRepoListBinding
    private lateinit var adapter: ListAdapter
    val db = WiproDataBase.getDatabase(WiproApp.instance)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        AndroidInjection.inject(this.activity)
        viewDataBinding = FragmentRepoListBinding.inflate(inflater, container, false).apply {
            viewmodel = ViewModelProviders.of(this@ListFragment).get(RepoListViewModel::class.java)
            setLifecycleOwner(viewLifecycleOwner)
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val decoration = DividerItemDecoration(requireContext(), HORIZONTAL)
        repo_list_rv.addItemDecoration(decoration)

        CoroutineScope(Dispatchers.IO).launch {
            viewDataBinding.viewmodel?.realdata?.postValue(db.wiproDao().getRecords())
        }

        try {
            if (savedInstanceState == null) {
                viewDataBinding.viewmodel?.realdata?.observe(viewLifecycleOwner, Observer {
                    if (it != null && it.isNotEmpty() && it.size > 0) {
                        adapter.updateRepoList(it)
                        title.text = it[0].headerTitle
                    }
                })

                if (NetworkConnecitity.isNetworkAvailable(requireContext())) {
                    viewDataBinding.viewmodel?.fetchRepoList()
                } else {
                    Toast.makeText(requireContext(), getString(R.string.network_message), Toast.LENGTH_SHORT).show()
                }

            }

        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        setupAdapter()
        // swipe refresh listener
        itemsswipetorefresh.setOnRefreshListener {
            if (NetworkConnecitity.isNetworkAvailable(requireContext())) {
                swipeRefresh()
            } else {
                Toast.makeText(requireContext(), getString(R.string.network_message), Toast.LENGTH_SHORT).show()
                itemsswipetorefresh.isRefreshing = false
            }
        }

    }

    // Swipe refresh api calling
    private fun swipeRefresh() {
        viewDataBinding.viewmodel?.fetchRepoList()
        itemsswipetorefresh.isRefreshing = false
    }

    // setting your adapter data
    private fun setupAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            adapter = ListAdapter(viewDataBinding.viewmodel!!)
            val layoutManager = LinearLayoutManager(activity)
            repo_list_rv.layoutManager = layoutManager

            // Setting item decoration for recycler view
            var itemDecoration: RecyclerView.ItemDecoration? = null
            while (repo_list_rv.itemDecorationCount > 0 && (repo_list_rv.getItemDecorationAt(0)?.let { itemDecoration = it }) != null) {
                repo_list_rv.removeItemDecoration(itemDecoration!!)
            }
            repo_list_rv.adapter = adapter
        }
    }

    companion object {
        private var INSTANCE: ListFragment? = null
        fun getInstance() = INSTANCE
                ?: ListFragment().also {
                    INSTANCE = it
                }
    }
}
