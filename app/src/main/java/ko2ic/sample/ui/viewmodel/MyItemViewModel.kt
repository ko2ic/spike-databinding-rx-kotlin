package ko2ic.sample.ui.viewmodel

import android.databinding.ObservableField
import ko2ic.sample.model.entity.Item
import ko2ic.sample.ui.viewmodel.common.Navigator

/**
 * Created by ishii_ko on 2017/06/29.
 */
class MyItemViewModel(val item: Item, val navigator: Navigator) : ViewModel {

    val name = ObservableField("")

    init {
        this.name.set(item.name)
    }

}