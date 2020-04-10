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
import com.ankit.jare.databinding.FragmentRepoListBinding
import com.ankit.jare.view.adapter.ListAdapter
import com.ankit.jare.view.utils.NetworkConnecitity
import com.ankit.jare.viewmodel.RepoListViewModel
import kotlinx.android.synthetic.main.fragment_repo_list.*
import org.jetbrains.anko.longToast

class ListFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentRepoListBinding
    private lateinit var adapter: ListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

        // Calling network status and call api for data
        if (NetworkConnecitity.isNetworkAvailable(requireContext())) {
            viewDataBinding.viewmodel?.fetchRepoList()
        } else {
            Toast.makeText(requireContext(), "Network not available please check it.", Toast.LENGTH_SHORT).show()
        }

        setupAdapter()
        setupObservers()

        // swipe refresh listener
        itemsswipetorefresh.setOnRefreshListener {
            if (NetworkConnecitity.isNetworkAvailable(requireContext())) {
                swipeRefresh()
            } else {
                Toast.makeText(requireContext(), "Network not available please check it.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // Obervib=ng data from viewmodel to update UI
    private fun setupObservers() {
        viewDataBinding.viewmodel?.repoListLive?.observe(viewLifecycleOwner, Observer {
            adapter.updateRepoList(it)
        })

        viewDataBinding.viewmodel?.toastMessage?.observe(viewLifecycleOwner, Observer {
            activity?.longToast(it)
        })
    }

    // Swipe refresh api calling
    private fun swipeRefresh() {
        viewDataBinding.viewmodel?.fetchRepoList()
        itemsswipetorefresh.isRefreshing = false
    }

    // setting your adapter dat
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
}
