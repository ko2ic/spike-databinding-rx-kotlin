package ko2ic.sample.viewmodel

import ko2ic.sample.ui.viewmodel.CollectionItemViewModel


class LoadingItemViewModel private constructor() : CollectionItemViewModel {

    private object Holder {
        val INSTANCE = LoadingItemViewModel()
    }

    companion object {
        val instance: LoadingItemViewModel by lazy { Holder.INSTANCE }
    }
}