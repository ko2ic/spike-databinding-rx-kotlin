package ko2ic.sample.ui.viewmodel.common

import android.databinding.Observable.OnPropertyChangedCallback
import android.databinding.ObservableField
import io.reactivex.Observable


object ObservableUtils {

    fun <T> toObservable(field: ObservableField<T>): Observable<T> {

        return Observable.create { e ->
            val initialValue = field.get()
            if (initialValue != null) {
                e.onNext(initialValue)
            }
            val callback = object : OnPropertyChangedCallback() {
                override fun onPropertyChanged(observable: android.databinding.Observable, i: Int) {
                    e.onNext(field.get()!!)
                }
            }
            field.addOnPropertyChangedCallback(callback)
            e.setCancellable { field.removeOnPropertyChangedCallback(callback) }
        }
    }

    fun <T> toField(observable: Observable<T>): ImmutableField<T> {
        return ImmutableField.create(observable)
    }
}
