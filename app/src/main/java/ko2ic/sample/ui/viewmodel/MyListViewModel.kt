package ko2ic.sample.ui.viewmodel

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import ko2ic.sample.model.entity.Item
import ko2ic.sample.ui.viewmodel.common.Navigator

/**
 * Created by ishii_ko on 2017/06/28.
 */
class MyListViewModel(val navigator: Navigator) : ListViewModel {

    private var tempItems = ArrayList<ViewModel>()

    override var items: List<ViewModel>
        get() {
            return tempItems
        }
        set(value) {
            this.tempItems = value as ArrayList<ViewModel>
        }

    private val itemsSource: Observable<List<Item>>

    init {
        val items = ArrayList<Item>()
        items.add(Item("item 1"));
        items.add(Item("item 2"));
        items.add(Item("item 3"));
        itemsSource = BehaviorSubject.createDefault(items);


        val viewModels = itemsSource.blockingFirst().map { item ->
            MyItemViewModel(item, navigator)
        }

        this.items = viewModels as List<ViewModel>
    }

}
