package ko2ic.sample.repository.http.common

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {
    private val original: RxJava2CallAdapterFactory

    init {
        original = RxJava2CallAdapterFactory.create()
    }

    companion object {
        fun create(): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory()
        }
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>
            = RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit))


    private class RxCallAdapterWrapper<R>(private val retrofit: Retrofit, private val wrapped: CallAdapter<R, *>) : CallAdapter<R, Any> {

        override fun responseType(): Type = wrapped.responseType()

        override fun adapt(call: Call<R>): Any {
            val result = wrapped.adapt(call)

            when (result) {
                is Single<*> -> {
                    result.onErrorResumeNext(Function { throwable ->
                        Single.error(HttpClientDefault.asHttpErrorTypeException(throwable))
                    })
                }
                is Observable<*> -> {
                    result.onErrorResumeNext(Function { throwable ->
                        Observable.error(HttpClientDefault.asHttpErrorTypeException(throwable))
                    })
                }
                is Completable -> {
                    result.onErrorResumeNext(Function { throwable ->
                        Completable.error(HttpClientDefault.asHttpErrorTypeException(throwable))
                    })
                }
            }
            return result
        }
    }
}
//
//class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
//    private final RxJava2CallAdapterFactory original;
//
//    private RxErrorHandlingCallAdapterFactory() {
//        original = RxJava2CallAdapterFactory.create();
//    }
//
//    public static CallAdapter.Factory create() {
//        return new RxErrorHandlingCallAdapterFactory();
//    }
//
//    @Override
//    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
//        return new RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit));
//    }
//
//    private static class RxCallAdapterWrapper<R> implements CallAdapter<R, Object> {
//        private final Retrofit retrofit;
//        private final CallAdapter<R, Object> wrapped;
//
//        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<R, Object> wrapped) {
//        this.retrofit = retrofit;
//        this.wrapped = wrapped;
//    }
//
//        @Override
//        public Type responseType() {
//            return wrapped.responseType();
//        }
//
//        @Override
//        public Object adapt(Call<R> call) {
//            Object result = wrapped.adapt(call);
//            if (result instanceof Single) {
//                return ((Single) result).onErrorResumeNext(new Function<Throwable, SingleSource >() {
//                    @Override
//                    public SingleSource apply(@Nonnull Throwable throwable) throws Exception {
//                        return Single.error(asHttpErrorTypeException(throwable));
//                    }
//                });
//            }
//            if (result instanceof Observable) {
//                return ((Observable) result).onErrorResumeNext(new Function<Throwable, ObservableSource>() {
//                    @Override
//                    public ObservableSource apply(@Nonnull Throwable throwable) throws Exception {
//                        return Observable.error(asHttpErrorTypeException(throwable));
//                    }
//                });
//            }
//
//            if (result instanceof Completable) {
//                return ((Completable) result).onErrorResumeNext(new Function<Throwable, CompletableSource >() {
//                    @Override
//                    public CompletableSource apply(@Nonnull Throwable throwable) throws Exception {
//                        return Completable.error(asHttpErrorTypeException(throwable));
//                    }
//                });
//            }
//
//            return result;
//        }
//
//        private HttpErrorTypeException asHttpErrorTypeException(Throwable throwable) {
//            if (throwable instanceof HttpException) {
//                Gson gson = new Gson();
//                Type listType = new TypeToken<Map<String, ?>>() {
//                }.getType();
//
//                HttpException excption = (HttpException) throwable;
//                String json = null;
//                try {
//                    json = excption.response().errorBody().string();
//                    Map<String, ?> map = gson.fromJson(json, listType);
//
//                    return new HttpErrorTypeException(new HttpErrorType.StatusCode(excption.code(), map));
//                } catch (IOException e) {
//                    return new HttpErrorTypeException(new HttpErrorType.Unknown(throwable));
//                }
//            } else if (throwable instanceof SocketTimeoutException) {
//                return new HttpErrorTypeException(new HttpErrorType.TimedoutError());
//            } else if (throwable instanceof UnknownHostException || throwable instanceof NoRouteToHostException) {
//                return new HttpErrorTypeException(new HttpErrorType.CannotFindHost());
//            } else if (throwable instanceof ConnectException) {
//                return new HttpErrorTypeException(new HttpErrorType.CannotConnectToHost());
//            }
//            return new HttpErrorTypeException(new HttpErrorType.Unknown(throwable));
//        }
//    }
//}