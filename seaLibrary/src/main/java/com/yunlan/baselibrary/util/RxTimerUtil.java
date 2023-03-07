package com.yunlan.baselibrary.util;


import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Rxjava2 实现定时器
 */

public class RxTimerUtil {

    private static Disposable mDisposable;

    /**
     * milliseconds 多少毫秒后执行next操作
     * @param milliseconds
     * @param next
     */
    public static void timer_mill(long milliseconds, final IRxNext next) {
        Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // 取消订阅
                        cancel();
                    }

                    @Override
                    public void onComplete() {
                        // 取消订阅
                        cancel();
                    }
                });
    }


    /**
     * seconds 多少秒后执行next操作
     * param seconds
     * param next
     */
    public static void timer_second(long secondss, final IRxNext next) {
        Observable.timer(secondss, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                        // 取消订阅
                        cancel();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // 取消订阅
                        cancel();
                    }

                    @Override
                    public void onComplete() {
                        // 取消订阅
                        cancel();
                    }
                });
    }


    /**
     * 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public static void interval(long milliseconds, final IRxNext next) {
        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // 取消订阅
                        cancel();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 倒计时第1种方法：Observable
     * 比如验证码
     */
    public static void intervalRange(long count, final IRxNext next) {
        //从0开始发射60个数字为：0-60依次输出，延时0s执行，每1s发射一次。
        Observable.intervalRange(0, count + 1, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        LogUtils.logm("倒计时===" + number);
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtils.logm("倒计时 onError＝＝＝＝＝＝＝＝");
                        // 取消订阅
                        cancel();
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.logm("倒计时完毕");
                        if (next != null) {
                            next.doonComplete();
                        }
                        cancel();
                    }
                });
    }


    /**
     * 倒计时第2种方法：Flowable
     * (比如验证码)
     */
    public static void intervalRange_Flowable(long count, final IRxNext next) {
        //从0开始发射60个数字为：0-60依次输出，延时0s执行，每1s发射一次。
        mDisposable = Flowable.intervalRange(0, count + 1, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtils.logm("倒计时===" + aLong);
                        if (next != null) {
                            next.doNext(aLong);
                        }
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        LogUtils.logm("倒计时完毕");
                        if (next != null) {
                            next.doonComplete();
                        }
                        cancel();
                    }
                })
                .subscribe();
    }


    /**
     * 取消订阅
     */

    public static void cancel() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
            LogUtils.logm("====定时器取消======");
        }
    }


    public interface IRxNext {
        void doNext(long number);
        void doonComplete();
    }
}
