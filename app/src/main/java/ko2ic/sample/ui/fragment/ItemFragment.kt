package ko2ic.sample.ui.fragment

import android.app.Fragment
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ko2ic.sample.BR
import ko2ic.sample.R
import ko2ic.sample.databinding.FragmentItemListBinding
import ko2ic.sample.ui.adapter.ItemViewTypeProvider
import ko2ic.sample.ui.adapter.RecyclerViewAdapter
import ko2ic.sample.ui.viewmodel.MyListViewModel
import ko2ic.sample.ui.viewmodel.ViewModel


class ItemFragment(val viewModel: MyListViewModel) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentItemListBinding>(
                inflater, R.layout.fragment_item_list, container, false)
        binding.recyclerView.adapter = RecyclerViewAdapter(viewModel, object : ItemViewTypeProvider {
            override fun getBindingVariableId(model: ViewModel): Int {
                return BR.viewModel
            }

            override fun getLayoutRes(model: ViewModel): Int {
                return R.layout.adapter_item
            }
        })

        val view = binding.root

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()

    }
}

