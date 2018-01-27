package ko2ic.sample.viewmodel

import android.view.MenuItem
import io.reactivex.subjects.PublishSubject
import ko2ic.sample.R
import ko2ic.sample.test.TestUtils
import ko2ic.sample.ui.viewmodel.common.TransitionType
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify


class MainViewModelTest {
    @Test
    fun onCreateMailClick() {
        val eventMock: PublishSubject<String> = TestUtils.mock()

        val target = MainViewModel(event = eventMock)
        target.onCreateMailClick().run()

        verify(eventMock).onNext("FAB押下した")
    }

    @Test
    fun onNavigationClick() {
        val eventMock: PublishSubject<TransitionType> = TestUtils.mock()
        val target = MainViewModel(transitionEvent = eventMock)

        val menuMock = Mockito.mock(MenuItem::class.java)
        Mockito.`when`(menuMock.itemId).thenReturn(R.id.nav_gallery)

        target.onNavigationClick(menuMock)

        verify(eventMock).onNext(TransitionType.ItemList)

    }

}