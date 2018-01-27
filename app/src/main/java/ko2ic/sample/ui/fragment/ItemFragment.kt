package ko2ic.sample.ui.fragment

import android.app.Fragment
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import ko2ic.sample.R
import ko2ic.sample.databinding.FragmentItemListBinding
import ko2ic.sample.ui.adapter.ItemViewTypeProvider
import ko2ic.sample.ui.adapter.RecyclerViewAdapter
import ko2ic.sample.ui.common.ApiErrorHandler
import ko2ic.sample.ui.viewmodel.CollectionItemViewModel
import ko2ic.sample.ui.viewmodel.common.TransitionType
import ko2ic.sample.viewmodel.MyItemViewModel
import ko2ic.sample.viewmodel.MyListViewModel
import ko2ic.sample.viewmodel.ViewModelHolder
import javax.inject.Inject


class ItemFragment @Inject constructor() : Fragment(), ViewModelHolder<MyListViewModel> {

    @Inject
    lateinit var apiErrorHandler: ApiErrorHandler

    @Inject
    override lateinit var viewModel: MyListViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentItemListBinding>(
                inflater, R.layout.fragment_item_list, container, false)

        binding.viewModel = viewModel

//        this.binding?.search?.setOnClickListener {
//            val text = this.binding?.inputSearch?.text.toString() ?: ""
//            viewModel.onSearchClick(text)
//        }

        viewModel.event.observeOn(AndroidSchedulers.mainThread()).subscribe { pair ->
            when (pair.second) {
                TransitionType.ItemDetail -> {
                    Snackbar.make(this@ItemFragment.view, pair.first.toString(), Snackbar.LENGTH_SHORT).show();
                }
                TransitionType.ItemDelete -> {
                    val deleteTarget = viewModel.viewModels.filterIndexed { index, viewModel ->
                        viewModel.id == pair.first.toLong()
                    }.first()
                    viewModel.viewModels.remove(deleteTarget)
                }
            }
        }

        val itemViewTypeProvider = object : ItemViewTypeProvider {
            override fun getLayoutRes(modelCollectionItem: CollectionItemViewModel): Int {
                // TODO: ここでViewModelを見てデザインを変える
                return R.layout.adapter_item
            }
        }

        binding.recyclerView.adapter = RecyclerViewAdapter<MyItemViewModel>(viewModel.viewModels, itemViewTypeProvider)

        val view = binding.root
        apiErrorHandler.subscribe(activity, view!!)
        return view
    }


    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDetach() {
        apiErrorHandler.dispose()
        viewModel.destroy()
        super.onDetach()

    }
}