package ko2ic.sample.viewmodel

import android.databinding.ObservableField
import io.reactivex.functions.Action
import io.reactivex.subjects.PublishSubject
import ko2ic.sample.model.entity.RepoForObjectbox
import ko2ic.sample.ui.viewmodel.common.TransitionType

class MyItemViewModelForObjectbox(val item: RepoForObjectbox, val event: PublishSubject<Pair<Int, TransitionType>>) : MyItemViewModel {

    override val haveAdmin = ObservableField("")
    override val name = ObservableField("")

    override val id: Long

    init {
        this.haveAdmin.set(item.permissions.target?.haveAdmin?.toString() ?: "Null")
        id = item.id
        name.set(item.name)
    }

    override fun onClickShow(): Action = Action {
        event.onNext(Pair(item.id.toInt(), TransitionType.ItemDetail))
    }

    override fun onClickDelete(): Action = Action {
        event.onNext(Pair(item.id.toInt(), TransitionType.ItemDelete))
    }

}