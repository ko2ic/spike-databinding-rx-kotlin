package ko2ic.sample.ui.adapter

import ko2ic.sample.ui.viewmodel.ViewModel


/**
 * Created by ishii_ko on 2017/06/30.
 */
interface ItemViewTypeProvider {
    fun getLayoutRes(model: ViewModel): Int
    fun getBindingVariableId(model: ViewModel): Int
}