package ko2ic.sample.di

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import ko2ic.sample.model.GitHub
import ko2ic.sample.model.MyObjectBox
import ko2ic.sample.repository.GitHubRepository
import javax.inject.Singleton

@Module
class DomainsModule {
    @Provides
    fun provideGitHub(repository: GitHubRepository) = GitHub(repository)

    @Singleton
    @Provides
    fun provideBoxStore(context: Context): BoxStore {
        val boxStore = MyObjectBox.builder().androidContext(context).build()
        if (BuildConfig.DEBUG) {
            val started = AndroidObjectBrowser(boxStore).start(context)
            Log.i("ObjectBrowser", "Started: " + started)
        }
        return boxStore
    }
}