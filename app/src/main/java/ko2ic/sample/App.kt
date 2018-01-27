package ko2ic.sample

import android.app.Activity
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import ko2ic.sample.di.DaggerAppComponent
import javax.inject.Inject

class App : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

        Realm.init(this)
        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
//        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }

    override fun onTerminate() {
        super.onTerminate()
    }

}