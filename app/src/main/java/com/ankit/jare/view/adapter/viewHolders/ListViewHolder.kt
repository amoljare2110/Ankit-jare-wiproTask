package com.ankit.jare.view.adapter.viewHolders

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ankit.jare.BR
import com.ankit.jare.wiproDataBase.WiproEntity
import com.ankit.jare.viewmodel.RepoListViewModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_repo_list_item.view.*


class ListViewHolder constructor(private val dataBinding: ViewDataBinding, private val repoListViewModel: RepoListViewModel)
    : RecyclerView.ViewHolder(dataBinding.root) {

    private val avatarImage = itemView.item_avatar!!
    fun setup(itemData: WiproEntity) {
        try {
            if (itemData != null) {
                dataBinding.setVariable(BR.itemData, itemData)
                dataBinding.executePendingBindings()

                if (itemData.imageHref != null && itemData.imageHref.isNotEmpty() && itemData.imageHref != " " && itemData.description != "Geography") {
                    Picasso.get().load(itemData.imageHref)
                            .into(avatarImage)
                }
            }

        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }
}