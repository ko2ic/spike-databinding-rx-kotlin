package ko2ic.sample.ui

import android.app.Activity
import android.content.Intent
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import ko2ic.sample.R
import ko2ic.sample.ui.activity.ItemListActivity
import ko2ic.sample.ui.viewmodel.common.Navigator


/**
 * Created by ishii_ko on 2017/06/22.
 */
class NavigatorImpl(activity: Activity) : Navigator {

    val source = activity
    val fragmentManager = activity.fragmentManager

    override fun navigateToItemList() = navigate(ItemListActivity::class.java)

    private fun navigate(destination: Class<out Activity>) {
        val intent = Intent(source, destination)
        source.startActivity(intent)
    }

    // TODO: 何故かkotlin extensionが動作しなくなった
    override fun closeDrawer() = (this.source.findViewById(R.id.drawer_layout) as DrawerLayout).closeDrawer(GravityCompat.START)
}