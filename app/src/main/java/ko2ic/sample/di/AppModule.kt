package ko2ic.sample.di

import dagger.Module
import dagger.Provides
import ko2ic.sample.App
import ko2ic.sample.model.GitHub
import ko2ic.sample.repository.TransactionTemplate
import ko2ic.sample.repository.local.common.TransactionTemplateImpl
import ko2ic.sample.ui.viewmodel.MainViewModel
import ko2ic.sample.ui.viewmodel.MyListViewModel


@Module
class AppModule {

    @Provides
    fun provideContext(application: App) = application.applicationContext

    @Provides
    fun provideTransactionTemplate(): TransactionTemplate = TransactionTemplateImpl()

    @Provides
    fun provideMainViewModel() = MainViewModel()

    @Provides
    fun provideMyListViewModel(tx: TransactionTemplate, domain: GitHub) = MyListViewModel(tx, domain)
}