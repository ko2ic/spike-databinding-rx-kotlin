package ko2ic.sample.ui.activity

import android.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import ko2ic.sample.ui.fragment.ItemFragment
import javax.inject.Inject


class ItemListActivity : android.support.v7.app.AppCompatActivity(), HasFragmentInjector {

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Fragment>

    override fun fragmentInjector() = androidInjector

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState)

        setContentView(ko2ic.sample.R.layout.activity_item_list)

        val itemFragment = ItemFragment()
        fragmentManager.beginTransaction()
                .replace(ko2ic.sample.R.id.container, itemFragment)
                .commitAllowingStateLoss()
    }
}
