package com.supcon.common.com_http.util;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wangshizhan on 16/5/6.
 */
public class RxErrorHandler {
    public static final FlowableTransformer<?, ?> mTransformer
            = new FlowableTransformer<Object, Object>() {
        @Override
        public Publisher<Object> apply(@NonNull Flowable<Object> observable) {
            return observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    @SuppressWarnings("unchecked")
    public static <T> FlowableTransformer<T, T> onError() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> observable) {
                return observable
                        .onErrorResumeNext(new Function<Throwable, Publisher<? extends T>>() {
                            @Override
                            public Publisher<? extends T> apply(@NonNull Throwable throwable) throws Exception {
                                return new Publisher<T>() {
                                    @Override
                                    public void subscribe(Subscriber<? super T> s) {

                                    }
                                };
                            }
                        });
            }


        };
    }
}
