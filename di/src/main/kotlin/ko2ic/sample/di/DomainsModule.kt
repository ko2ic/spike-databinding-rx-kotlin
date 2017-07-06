package ko2ic.sample.di

import dagger.Module
import dagger.Provides
import ko2ic.sample.model.GitHub
import ko2ic.sample.repository.GitHubRepository

@Module
class DomainsModule {
    @Provides
    fun provideGitHub(repository: GitHubRepository) = GitHub(repository)

}