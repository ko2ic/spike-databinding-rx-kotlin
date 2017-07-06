package ko2ic.sample.ui.common

import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import io.reactivex.disposables.Disposable
import ko2ic.sample.model.valueobject.enums.HttpErrorType
import ko2ic.sample.ui.viewmodel.common.ApiErrorState

/**
 * APIエラーが起きた時の処理。
 * Viewに依存する処理がここに書かれる
 */
class ApiErrorHandler {

    private lateinit var disposeable: Disposable

    fun subscribe(view: View) {
        disposeable = ApiErrorState.instance.error.subscribe { httpErrorType ->
            when (httpErrorType) {
                is HttpErrorType.None -> {
                    // 何もしない
                }
                is HttpErrorType.Unknown -> {
                    Log.e("ApiErrorHandler", "HttpErrorType.Unknown", httpErrorType.t)
                }
                is HttpErrorType.TimedoutError -> {
                    Log.e("ApiErrorHandler", "HttpErrorType.TimedoutError")
                }
                is HttpErrorType.CannotFindHost -> {
                    Log.e("ApiErrorHandler", "HttpErrorType.CannotFindHost")
                }
                is HttpErrorType.CannotConnectToHost -> {
                    Log.e("ApiErrorHandler", "HttpErrorType.CannotConnectToHost")
                }
                is HttpErrorType.StatusCode -> {
                    when (httpErrorType.code) {
                        422 -> {
                            Snackbar.make(view, httpErrorType.map.toString(), Snackbar.LENGTH_SHORT).show();
                        }
                        else -> Snackbar.make(view, "なんかのエラー", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }.let {
                // パターンマッチの対象を記述し忘れた場合にコンパイルエラーを出したい為のlet
            }
        }
    }

    fun dispose() {
        if (disposeable != null && !disposeable.isDisposed) {
            this.disposeable.dispose()
        }
    }
}
