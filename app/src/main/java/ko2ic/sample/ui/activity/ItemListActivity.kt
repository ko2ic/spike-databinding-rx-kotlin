package ko2ic.sample.ui.activity

import ko2ic.sample.ui.NavigatorImpl
import ko2ic.sample.ui.fragment.ItemFragment
import ko2ic.sample.ui.viewmodel.MyListViewModel


class ItemListActivity : android.support.v7.app.AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ko2ic.sample.R.layout.activity_item_list)

        val viewModel = MyListViewModel(NavigatorImpl(this))

        val itemFragment = ItemFragment(viewModel)
        fragmentManager.beginTransaction()
                .replace(ko2ic.sample.R.id.container, itemFragment)
                .commitAllowingStateLoss()

    }
}
