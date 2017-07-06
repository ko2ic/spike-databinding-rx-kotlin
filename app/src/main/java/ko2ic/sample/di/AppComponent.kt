package ko2ic.sample.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import ko2ic.sample.App
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, BuildersModule::class, AppModule::class, DomainsModule::class, InfrastructuresModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: App): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)
}
