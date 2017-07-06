package ko2ic.sample.common.repository.http

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class HttpClient(val locator: HttpClientLocator) {

    inline fun <R, reified T> call(call: (T) -> Single<R>): Single<R> {

        val client = locator.lookup(clazz = T::class.java)

        val single = call(client)
        return single.subscribeOn(Schedulers.io()).onErrorResumeNext {
            // TODO: RxErrorHandlingCallAdapterFactoryが動かない。ここでやる？
            Single.error(HttpClientBase.asHttpErrorTypeException(it))
        }
    }
}