package ko2ic.sample.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View


fun AppCompatActivity.setToolBarWithUpButton(toolbar: Toolbar, title: String = "", onClickListener: () -> Unit = { finish() }) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener { onClickListener() }
    actionBarTitle = title
}

fun AppCompatActivity.setToolBar(toolbar: Toolbar, title: String = "") {
    setSupportActionBar(toolbar)
    actionBarTitle = title
}

var AppCompatActivity.actionBarTitle: String?
    get() = supportActionBar?.title?.toString()
    set(value) {
        supportActionBar?.setTitle(value)
    }


fun Activity.alertDialog(messageResId: Int, positiveListner: DialogInterface.OnClickListener? = null, isCancelable: Boolean = true): AlertDialog {
    return alertDialog(messageResId = messageResId, negativeLabelResId = null, positiveListener = positiveListner, isCancelable = isCancelable)
}

fun Activity.alertDialog(messageResId: Int, messageArgument: String?, positiveListner: DialogInterface.OnClickListener? = null, isCancelable: Boolean = true): AlertDialog {
    return alertDialog(messageResId = messageResId, messageArgument = messageArgument, negativeLabelResId = null, isCancelable = isCancelable)
}

fun Activity.alertDialog(view: View,
                         positiveLabelResId: Int? = android.R.string.ok,
                         positiveListener: DialogInterface.OnClickListener? = null,
                         negativeLabelResId: Int? = android.R.string.cancel,
                         negativeListener: DialogInterface.OnClickListener? = null
): AlertDialog {
    val alertDialog = AlertDialog.Builder(this).apply {
        setView(view)
        if (positiveLabelResId != null) {
            setPositiveButton(positiveLabelResId, positiveListener)
        }
        if (negativeLabelResId != null) {
            setNegativeButton(negativeLabelResId, negativeListener)
        }
    }.create()
    alertDialog.show()
    return alertDialog
}

/**
 * activityが破棄されたら一緒になくなっていい場合のDialogを表示する.
 */
fun Activity.alertDialog(messageResId: Int,
                         positiveLabelResId: Int? = android.R.string.ok,
                         positiveListener: DialogInterface.OnClickListener? = null,
                         negativeLabelResId: Int? = android.R.string.cancel,
                         negativeListener: DialogInterface.OnClickListener? = null,
                         titleResId: Int? = null,
                         messageArgument: String? = null,
                         isCancelable: Boolean = true): AlertDialog {
    val alertDialog = AlertDialog.Builder(this).apply {
        setCancelable(isCancelable)
        if (messageArgument != null) {
            setMessage(this@alertDialog.getString(messageResId, messageArgument))
        } else {
            setMessage(this@alertDialog.getString(messageResId))
        }

        if (titleResId != null) {
            setTitle(titleResId)
        }
        if (positiveLabelResId != null) {
            setPositiveButton(positiveLabelResId, positiveListener)
        }
        if (negativeLabelResId != null) {
            setNegativeButton(negativeLabelResId, negativeListener)
        }
    }.create()
    alertDialog.show()
    return alertDialog
}