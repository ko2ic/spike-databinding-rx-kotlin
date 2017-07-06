package ko2ic.sample.ui.viewmodel

import android.databinding.ObservableField
import io.reactivex.functions.Action
import io.reactivex.subjects.PublishSubject
import ko2ic.sample.model.entity.Repo
import ko2ic.sample.ui.viewmodel.common.TransitionType

class MyItemViewModel(val item: Repo, val event: PublishSubject<Pair<Int, TransitionType>>) : CollectionItemViewModel {

    val name = ObservableField("")

    init {
        this.name.set(item.name)
    }

    fun onClickShow(): Action = Action {
        event.onNext(Pair(item.id, TransitionType.ItemDetail))
    }

    fun onClickDelete(): Action = Action {
        event.onNext(Pair(item.id, TransitionType.ItemDelete))
    }

}