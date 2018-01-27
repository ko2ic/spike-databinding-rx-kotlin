package ko2ic.sample.di

import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import ko2ic.sample.common.repository.TransactionTemplateForObjectbox
import ko2ic.sample.common.repository.http.HttpClient
import ko2ic.sample.repository.GitHubRepository
import ko2ic.sample.repository.GithubRepositoryImpl
import ko2ic.sample.repository.http.common.HttpClientDefault
import ko2ic.sample.repository.local.GitHubLocalStore
import javax.inject.Singleton

@Module
class InfrastructuresModule {

    @Provides
    @Singleton
    fun provideGitHubRepository(store: GitHubLocalStore, client: HttpClient, tx: TransactionTemplateForObjectbox): GitHubRepository = GithubRepositoryImpl(store, client, tx)

    @Provides
    @Singleton
    fun provideGitHubLocalStore(boxStore: BoxStore) = GitHubLocalStore(boxStore)

    @Provides
    @Singleton
    fun provideHttpClient(locator: HttpClientDefault) = HttpClient(locator)

    @Provides
    @Singleton
    fun provideHttpClientLocator() = HttpClientDefault()


}