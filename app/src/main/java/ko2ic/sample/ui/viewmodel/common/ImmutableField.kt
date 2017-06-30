package ko2ic.sample.ui.viewmodel.common

import android.databinding.ObservableField
import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * 出力だけで利用するフィールド（ObservableField）
 */
class ImmutableField<T> private constructor(source: Observable<T>) : ObservableField<T>() {
    private val source: Observable<T>
    private val subscriptions = HashMap<android.databinding.Observable.OnPropertyChangedCallback, Disposable>()

    init {
        this.source = source.doOnNext { t ->
            super@ImmutableField.set(t)
        }.doOnError { throwable ->
            Log.e("OutputField", "onError in source observable", throwable)
        }.onErrorResumeNext(Observable.empty<T>()).share()
    }

    /**
     * setを許可することでRXで値を変更され、viewmodelの値が予期しない値にならないように何もしないようにオーバライドしています。
     * これにより、常にrxの値の結果と同じ値になります。
     */
    override fun set(value: T) = Unit

    @Synchronized
    override fun addOnPropertyChangedCallback(callback: android.databinding.Observable.OnPropertyChangedCallback) {
        super.addOnPropertyChangedCallback(callback)
        subscriptions.put(callback, source.subscribe())
    }

    @Synchronized
    override fun removeOnPropertyChangedCallback(callback: android.databinding.Observable.OnPropertyChangedCallback) {
        super.removeOnPropertyChangedCallback(callback)
        val subscription = subscriptions.remove(callback)
        if (subscription != null && !subscription.isDisposed) {
            subscription.dispose()
        }
    }

    companion object {
        fun <U> create(source: Observable<U>): ImmutableField<U> {
            return ImmutableField(source)
        }
    }
}
