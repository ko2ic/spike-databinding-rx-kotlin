package ko2ic.sample

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import ko2ic.sample.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun onClickTest() {

        // TODO: バグってるからandroid plugin 3では動作しないようです。
        // https://github.com/realm/realm-java/issues/4662

    }
}
