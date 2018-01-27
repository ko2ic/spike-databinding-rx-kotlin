package ko2ic.sample.di

import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import ko2ic.sample.App
import ko2ic.sample.common.repository.TransactionTemplate
import ko2ic.sample.common.repository.TransactionTemplateForObjectbox
import ko2ic.sample.common.repository.local.TransactionTemplateForObjectboxImpl
import ko2ic.sample.common.repository.local.TransactionTemplateImpl
import ko2ic.sample.model.GitHub
import ko2ic.sample.ui.common.ApiErrorHandler
import ko2ic.sample.viewmodel.MainViewModel
import ko2ic.sample.viewmodel.MyListViewModel


@Module
class AppModule {

    @Provides
    fun provideContext(application: App) = application.applicationContext

    @Provides
    fun provideTransactionTemplate(): TransactionTemplate = TransactionTemplateImpl()

    @Provides
    fun provideTransactionTemplateForObjectbox(boxStore: BoxStore): TransactionTemplateForObjectbox = TransactionTemplateForObjectboxImpl(boxStore)


    @Provides
    fun providerApiErrorHandler(): ApiErrorHandler = ApiErrorHandler()

    @Provides
    fun provideMainViewModel() = MainViewModel()

    @Provides
    fun provideMyListViewModel(domain: GitHub) = MyListViewModel(domain)
}