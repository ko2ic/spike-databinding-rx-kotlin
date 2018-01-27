package ko2ic.sample.viewmodel

import android.databinding.ObservableField
import android.view.MenuItem
import io.reactivex.functions.Action
import io.reactivex.subjects.PublishSubject
import ko2ic.sample.R
import ko2ic.sample.ui.viewmodel.common.ImmutableField
import ko2ic.sample.ui.viewmodel.common.ObservableUtils
import ko2ic.sample.ui.viewmodel.common.TransitionType


class MainViewModel(val event: PublishSubject<String> = PublishSubject.create<String>(), val transitionEvent: PublishSubject<TransitionType> = PublishSubject.create<TransitionType>()) {

    val input = ObservableField("")
    val output: ImmutableField<String>

    init {
        val inputObservable = ObservableUtils.toObservable(input)
        this.output = ObservableUtils.toField(inputObservable)
    }

    fun onCreateMailClick(): Action = Action {
        event.onNext("FAB押下した")
    }

    fun onNavigationClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_gallery -> {
                transitionEvent.onNext(TransitionType.ItemList)
            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        return true
    }
}