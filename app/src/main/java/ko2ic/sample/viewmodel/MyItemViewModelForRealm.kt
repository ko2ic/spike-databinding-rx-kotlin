package ko2ic.sample.viewmodel

import android.databinding.ObservableField
import io.reactivex.functions.Action
import io.reactivex.subjects.PublishSubject
import ko2ic.sample.model.entity.Repo
import ko2ic.sample.ui.viewmodel.common.TransitionType

class MyItemViewModelForRealm(val item: Repo, val event: PublishSubject<Pair<Int, TransitionType>>) : MyItemViewModel {

    override val haveAdmin = ObservableField("")
    override val name = ObservableField("")

    override val id: Long

    init {
        this.haveAdmin.set(item.permissions?.haveAdmin?.toString() ?: "Null")
        id = item.id.toLong()
        name.set(item.name)
    }

    override fun onClickShow(): Action = Action {
        event.onNext(Pair(item.id, TransitionType.ItemDetail))
    }

    override fun onClickDelete(): Action = Action {
        event.onNext(Pair(item.id, TransitionType.ItemDelete))
    }

}