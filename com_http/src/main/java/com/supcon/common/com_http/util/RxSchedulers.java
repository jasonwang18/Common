package com.supcon.common.com_http.util;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wangshizhan on 16/5/6.
 */
public class RxSchedulers {
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
    public static <T> FlowableTransformer<T, T> io_main() {
        return (FlowableTransformer<T, T>) new FlowableTransformer<Object, Object>() {
            @Override
            public Publisher<Object> apply(@NonNull Flowable<Object> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }


        };
    }
}
