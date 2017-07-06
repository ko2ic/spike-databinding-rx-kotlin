package ko2ic.sample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ko2ic.sample.ui.activity.ItemListActivity
import ko2ic.sample.ui.activity.MainActivity
import ko2ic.sample.ui.fragment.ItemFragment


@Module
abstract class BuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeItemListActivity(): ItemListActivity

    @ContributesAndroidInjector
    abstract fun contributeItemFragment(): ItemFragment

}