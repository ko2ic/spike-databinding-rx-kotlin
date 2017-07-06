package ko2ic.sample.ui.common

import android.app.Activity
import android.app.AlertDialog
import android.support.design.widget.Snackbar
import android.view.View
import io.reactivex.disposables.Disposable
import ko2ic.sample.common.Logger
import ko2ic.sample.common.model.valueobject.enums.HttpErrorType
import ko2ic.sample.ui.viewmodel.common.ApiErrorState
import javax.inject.Inject

/**
 * APIエラーが起きた時の処理。
 * Viewに依存する処理がここに書かれる
 */
class ApiErrorHandler @Inject constructor() {

    private lateinit var disposeable: Disposable // 必ず最初に設定すること

    private var alertDialog: AlertDialog? = null

    fun subscribe(activity: Activity, view: View? = null) {
        disposeable = ApiErrorState.instance.error.subscribe({ httpErrorType ->
            when (httpErrorType) {
                is HttpErrorType.None -> {
                    // 何もしない
                }
                is HttpErrorType.Unknown -> {
                    Logger.e(httpErrorType.t)
                    //alertDialog = activity.alertDialog(R.string.api_internal_server_error)
                }
                is HttpErrorType.TimedoutError -> {
                    Logger.e(httpErrorType.t)
                    //alertDialog = activity.alertDialog(R.string.api_connection_timeout)
                }
                is HttpErrorType.CannotFindHost -> {
                    Logger.e(httpErrorType.t)
                    //alertDialog = activity.alertDialog(R.string.api_connection_refuse)
                }
                is HttpErrorType.CannotConnectToHost -> {
                    Logger.e(httpErrorType.t)
                    //alertDialog = activity.alertDialog(R.string.api_connection_refuse)
                }
                is HttpErrorType.StatusCode -> {
                    when {
                        httpErrorType.isBadRequest() -> {
                            Logger.e(httpErrorType.t)
                            val alertDialog = AlertDialog.Builder(activity).apply {
                                setMessage("Bad Reqeust")
                                setPositiveButton(android.R.string.ok, null)
                            }.create()
                            alertDialog.show()
                            this.alertDialog = alertDialog
                        }
                        httpErrorType.isUnauthorized() -> {
                            //activity.startActivity(StartActivity.intent(activity))
                            activity.overridePendingTransition(0, 0) // 自動遷移の場合はアニメーションなしで遷移
                        }
                        httpErrorType.isPaymentRequired() -> Snackbar.make(view!!, "課金画面遷移", Snackbar.LENGTH_SHORT).show();
                        httpErrorType.isForbidden() -> {
                            Logger.e(httpErrorType.t)
                            //alertDialog = activity.alertDialog(R.string.api_forbbiden)
                        }
                        httpErrorType.isNotFound() -> {
                            Logger.e(httpErrorType.t)
                            Snackbar.make(view!!, "利用しようとした機能が存在しません。", Snackbar.LENGTH_SHORT).show();
                        }
                        httpErrorType.isInternalServerError() -> {
                            Logger.e(httpErrorType.t)
                            //alertDialog = activity.alertDialog(R.string.api_internal_server_error)
                        }
                        httpErrorType.isServiceUnavailable() -> {
                            Snackbar.make(view!!, "TODO: メンテナンス画面", Snackbar.LENGTH_SHORT).show();
                        }
                        else -> {
                            Logger.e(httpErrorType.t)
                            //alertDialog = activity.alertDialog(R.string.api_internal_server_error)
                        }
                    }
                }
            }
        }, { e -> Logger.e(e) })
    }

    fun dispose() {
        alertDialog?.dismiss()
        if (!disposeable.isDisposed) {
            this.disposeable.dispose()
        }
    }
}
