package ko2ic.sample.ui.viewmodel

import android.databinding.ObservableField
import io.reactivex.functions.Action
import io.reactivex.subjects.PublishSubject
import ko2ic.sample.model.entity.Repo

class MyItemViewModel(val item: Repo, val event: PublishSubject<String> = PublishSubject.create<String>(), val deleteEvent: PublishSubject<MyItemViewModel> = PublishSubject.create<MyItemViewModel>()) : CollectionItemViewModel {

    val name = ObservableField("")

    init {
        this.name.set(item.name)
    }

    fun onClickShow(): Action = Action {
        event.onNext(this.name.get())
    }

    fun onClickDelete(): Action = Action {
        deleteEvent.onNext(this)
    }

}