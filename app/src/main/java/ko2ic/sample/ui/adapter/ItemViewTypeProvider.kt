package ko2ic.sample.ui.adapter

import ko2ic.sample.BR
import ko2ic.sample.ui.viewmodel.CollectionItemViewModel

interface ItemViewTypeProvider {

    fun getLayoutRes(modelCollectionItem: CollectionItemViewModel): Int

    fun getBindingVariableId(modelCollectionItem: CollectionItemViewModel) = BR.viewModel
}