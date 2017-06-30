package ko2ic.sample.ui.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.ViewGroup
import ko2ic.sample.ui.viewmodel.ListViewModel


/**
 * Created by ishii_ko on 2017/06/29.
 */
class RecyclerViewAdapter(private val listViewModel: ListViewModel, private val viewTypeProvider: ItemViewTypeProvider) : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {

    private var inflater: LayoutInflater? = null

    override fun getItemCount(): Int {
        return listViewModel.items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        if (inflater == null) {
            this.inflater = from(parent.getContext());
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = listViewModel.items[position]

        holder.binding.setVariable(viewTypeProvider.getBindingVariableId(item), item)
        holder.binding.executePendingBindings()
    }

    class ItemViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun getItemViewType(position: Int): Int {
        return viewTypeProvider.getLayoutRes(listViewModel.items[position])
    }

}

