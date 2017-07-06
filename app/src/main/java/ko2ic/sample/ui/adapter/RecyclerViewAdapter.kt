package ko2ic.sample.ui.adapter

import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.ViewGroup
import ko2ic.sample.ui.viewmodel.CollectionItemViewModel

open class RecyclerViewAdapter<T>(private val list: ObservableList<T>, private val viewTypeProvider: ItemViewTypeProvider) : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() where T : CollectionItemViewModel {

    init {
        list.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
            override fun onChanged(contributorViewModels: ObservableList<T>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(contributorViewModels: ObservableList<T>, positionStart: Int, itemCount: Int) {
                notifyItemRangeChanged(positionStart, itemCount)
            }

            override fun onItemRangeInserted(contributorViewModels: ObservableList<T>, positionStart: Int, itemCount: Int) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeMoved(contributorViewModels: ObservableList<T>, fromPosition: Int, toPosition: Int, itemCount: Int) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeRemoved(contributorViewModels: ObservableList<T>, positionStart: Int, itemCount: Int) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }
        })
    }

    private var inflater: LayoutInflater? = null

    fun getItem(position: Int) = list.elementAtOrNull(position)

    override fun getItemCount() = list.count()
    override fun getItemViewType(position: Int) = viewTypeProvider.getLayoutRes(list[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        if (inflater == null) {
            this.inflater = from(parent.getContext());
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = list[position]
        holder.binding.setVariable(viewTypeProvider.getBindingVariableId(item), item)
        holder.binding.executePendingBindings()
    }

    class ItemViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    }


}

