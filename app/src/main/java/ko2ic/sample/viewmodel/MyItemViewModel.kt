package ko2ic.sample.viewmodel

import android.databinding.ObservableField
import io.reactivex.functions.Action
import ko2ic.sample.ui.viewmodel.CollectionItemViewModel


interface MyItemViewModel : CollectionItemViewModel {

    val haveAdmin: ObservableField<String>

    val name: ObservableField<String>

    val id: Long

    fun onClickShow(): Action
    fun onClickDelete(): Action
}