package ko2ic.sample.test

import io.reactivex.functions.Consumer
import org.assertj.core.api.Assertions
import org.mockito.Mockito


class TestUtils private constructor() {

    companion object {
        fun <T> check(consumer: Consumer<T>): io.reactivex.functions.Predicate<T> {
            return io.reactivex.functions.Predicate { t ->
                try {
                    consumer.accept(t)
                } catch(e: AssertionError) {
                    Assertions.assertThat(e).hasMessage("Please confirm Actual and expected")
                }
                true
            }
        }

        inline fun <reified T : Any> mock() = Mockito.mock(T::class.java)
    }
}