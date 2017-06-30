package ko2ic.sample.ui.viewmodel

import android.databinding.ObservableField
import android.view.MenuItem
import io.reactivex.functions.Action
import ko2ic.sample.R
import ko2ic.sample.ui.viewmodel.common.ImmutableField
import ko2ic.sample.ui.viewmodel.common.Navigator
import ko2ic.sample.ui.viewmodel.common.ObservableUtils

/**
 * Created by ishii_ko on 2017/06/22.
 */
class MainViewModel(val navigator: Navigator) {
    val input = ObservableField("")
    val output: ImmutableField<String>

    init {
        val inputObservable = ObservableUtils.toObservable(input)
        this.output = ObservableUtils.toField(inputObservable)
    }

    fun onCreateMailClick(): Action = Action {
        input.set("FAB押下した")
    }

    fun onNavigationClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {
                navigator.navigateToItemList()
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
        navigator.closeDrawer()
        return true
    }
}