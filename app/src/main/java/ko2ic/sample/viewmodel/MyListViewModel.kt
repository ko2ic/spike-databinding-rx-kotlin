package ko2ic.sample.ui.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ko2ic.sample.common.repository.TransactionTemplate
import ko2ic.sample.model.GitHub
import ko2ic.sample.ui.viewmodel.common.TransitionType
import javax.inject.Inject

class MyListViewModel @Inject constructor(var tx: TransactionTemplate, val domain: GitHub) : ViewModel {

    val input = ObservableField("")

    val viewModels = ObservableArrayList<MyItemViewModel>()

    val event = PublishSubject.create<Pair<Int, TransitionType>>()

    private val compositeDisposable = CompositeDisposable()

//    fun onSearchClick(text: String) {
//        if (text.isNotEmpty()) {
//            val usecase = GitHubUseCase().fetchRepos(text, 1)
//
//            val disposable = usecase.map { repos ->
//                repos.items.map { repo ->
//                    MyItemViewModel(repo)
//                }
//            }.observeOn(AndroidSchedulers.mainThread()).subscribe(this::render)
//
//            compositeDisposable.add(disposable)
//        }
//    }

    fun onSearchClick(): Action = Action {
        val ovservable = domain.fetchRepos(input.get(), 1)

        val disposable = ovservable.map { repos ->
            repos.items.map { repo ->
                MyItemViewModel(repo, event)
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::render, this::error)

        compositeDisposable.add(disposable)
    }


    fun destroy() {
        compositeDisposable.clear()
    }

    private fun render(itemViewModels: List<MyItemViewModel>) {
        viewModels.clear()
        viewModels.addAll(itemViewModels)
    }
}
