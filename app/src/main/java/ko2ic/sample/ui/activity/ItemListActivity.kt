package ko2ic.sample.ui.activity

import android.app.Fragment
import android.databinding.DataBindingUtil
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import jp.ra9.ui.controllers.FragmentHolder
import ko2ic.sample.R
import ko2ic.sample.databinding.ActivityItemListBinding
import ko2ic.sample.ui.fragment.ItemFragment
import ko2ic.sample.viewmodel.MyListViewModel
import ko2ic.sample.viewmodel.ViewModelHolder
import javax.inject.Inject


class ItemListActivity : android.support.v7.app.AppCompatActivity(), HasFragmentInjector, ViewModelHolder<MyListViewModel>, FragmentHolder<ItemFragment> {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    override lateinit var fragment: ItemFragment

    @Inject
    override lateinit var viewModel: MyListViewModel

    override fun fragmentInjector() = androidInjector

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityItemListBinding>(this, R.layout.activity_item_list)
        binding.viewModel = viewModel

        fragmentManager.beginTransaction()
                .replace(ko2ic.sample.R.id.container, fragment)
                .commitAllowingStateLoss()
    }
}
