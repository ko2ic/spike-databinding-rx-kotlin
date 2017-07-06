package ko2ic.sample.ui.fragment

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ko2ic.sample.R
import ko2ic.sample.databinding.FragmentItemListBinding
import ko2ic.sample.ui.adapter.ItemViewTypeProvider
import ko2ic.sample.ui.adapter.RecyclerViewAdapter
import ko2ic.sample.ui.common.ApiErrorHandler
import ko2ic.sample.ui.viewmodel.CollectionItemViewModel
import ko2ic.sample.ui.viewmodel.MyItemViewModel
import ko2ic.sample.ui.viewmodel.MyListViewModel
import javax.inject.Inject


@SuppressLint("ValidFragment")
class ItemFragment() : Fragment() {

    @Inject
    lateinit var viewModel: MyListViewModel

    private val apiErrorHandler = ApiErrorHandler()

    var binding: FragmentItemListBinding? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        this.binding = DataBindingUtil.inflate<FragmentItemListBinding>(
                inflater, R.layout.fragment_item_list, container, false)

        (this.binding as FragmentItemListBinding?)?.viewModel = viewModel

//        this.binding?.search?.setOnClickListener {
//            val text = this.binding?.inputSearch?.text.toString() ?: ""
//            viewModel.onSearchClick(text)
//        }

        val itemViewTypeProvider = object : ItemViewTypeProvider {
            override fun getLayoutRes(modelCollectionItem: CollectionItemViewModel): Int {
                // TODO: ここでViewModelを見てデザインを変える
                return R.layout.adapter_item
            }
        }

        binding?.recyclerView?.adapter = object : RecyclerViewAdapter<MyItemViewModel>(viewModel.viewModels, itemViewTypeProvider) {
            private val disposables = HashMap<Int, Disposable>()
            private val deleteDisposables = HashMap<Int, Disposable>()
            override fun onViewAttachedToWindow(holder: ItemViewHolder) {
                super.onViewAttachedToWindow(holder)
                val dispose = getItem(holder.adapterPosition)?.event?.observeOn(AndroidSchedulers.mainThread())?.share()?.subscribe { name ->
                    Snackbar.make(this@ItemFragment.view, name, Snackbar.LENGTH_SHORT).show();
                }
                if (dispose != null) {
                    disposables.put(holder.adapterPosition, dispose)
                }
                val deleteDispose = getItem(holder.adapterPosition)?.deleteEvent?.observeOn(AndroidSchedulers.mainThread())?.share()?.subscribe { item ->
                    viewModel.viewModels.remove(item)
                }
                if (deleteDispose != null) {
                    deleteDisposables.put(holder.adapterPosition, deleteDispose)
                }
            }

            override fun onViewDetachedFromWindow(holder: ItemViewHolder) {
                super.onViewDetachedFromWindow(holder)
                val dispose = disposables[holder.adapterPosition]
                dispose?.dispose()
                val deleteDispose = deleteDisposables[holder.adapterPosition]
                deleteDispose?.dispose()
            }

            override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
                super.onDetachedFromRecyclerView(recyclerView)
                disposables.values.forEach { value ->
                    value.dispose()
                }
                deleteDisposables.values.forEach { value ->
                    value.dispose()
                }

            }
        }

        val view = binding?.root
        apiErrorHandler.subscribe(view!!)
        return view
    }


    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDetach() {
        apiErrorHandler.dispose()
        binding?.recyclerView?.adapter = null
        viewModel.destroy()
        super.onDetach()

    }
}

