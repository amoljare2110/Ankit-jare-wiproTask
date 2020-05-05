package com.ankit.jare.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ankit.jare.databinding.ViewRepoListItemBinding
import com.ankit.jare.wiproDataBase.WiproEntity
import com.ankit.jare.view.adapter.viewHolders.ListViewHolder
import com.ankit.jare.viewmodel.RepoListViewModel

class ListAdapter(private val repoListViewModel: RepoListViewModel) : RecyclerView.Adapter<ListViewHolder>() {
    private var repoList: List<WiproEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ViewRepoListItemBinding.inflate(inflater, parent, false)
        return ListViewHolder(dataBinding, repoListViewModel)
    }

    override fun getItemCount() = repoList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.setup(repoList[position],position)
    }

    fun updateRepoList(repoList: List<WiproEntity>) {
        this.repoList = repoList
        notifyDataSetChanged()
    }
}