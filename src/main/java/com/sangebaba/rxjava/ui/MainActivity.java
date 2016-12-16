package com.sangebaba.rxjava.ui;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sangebaba.rxjava.R;
import com.sangebaba.rxjava.base.BaseActivity;
import com.sangebaba.rxjava.util.DebugLog;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_click)
    Button btnClick;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btn_click)
    public void onClick() {
        DebugLog.e("test");

        // TODO: 2016/12/16 创建观察者 observer
        final Observer<String> stringObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                DebugLog.i("onCompleted======");
            }

            @Override
            public void onError(Throwable e) {
                DebugLog.i(e + "=========");

            }

            @Override
            public void onNext(String s) {
                DebugLog.i(s + "==========");
            }

        };
        // TODO: 2016/12/16 创建被观察者 observerable
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("111");
                subscriber.onNext("222");
                subscriber.onNext("333");
                subscriber.onNext("444");
                subscriber.onCompleted();
            }
        });
        //// TODO: 2016/12/16 等价于just()   from()
        Observable.just("111", "222", "333");
        Observable.from(new String[]{"111", "222", "333"});

        // TODO: 2016/12/16  创建subscribe() 方法关联 observer obserable
        observable.subscribe(stringObserver);


        // TODO: 2016/12/16 示例一 打印字符串数组
        Observable.from(new String[]{"1", "2", "3"}).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                DebugLog.i(s + "=====");
            }
        });
        // TODO: 2016/12/16  示例二 取得id图片并显示
        final int programmer = R.drawable.programmer;
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(programmer);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())  //指定事件产生的线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        DebugLog.i("指定线程");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())  //指定事件消费的线程
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugLog.i("Error=====");

                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        ivPic.setImageDrawable(drawable);

                    }
                });


        class Course {

            String name = "";

            public Course(String name) {
                this.name = name;
            }

            public String getCourse() {
                return " china  " + name;
            }
        }

        class Student {
            String name = "";

            Course course[];

            public Student(String name) {
                this.name = name;
                course = new Course[]{new Course(getName()), new Course(getName()), new Course(getName()), new Course(getName())};
            }


            public String getName() {
                return name;
            }
        }

        Student[] students = new Student[]{new Student("zhangsan"), new Student("lisi"), new Student("wangwu"), new Student("maliu")};

        // TODO: 2016/12/16  flatmap 变换
        Observer<Course> observer = new Observer<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {
                DebugLog.i(course.name + "====");

            }
        };
        Observable.from(students).flatMap(new Func1<Student, Observable<Course>>() {
            @Override
            public Observable<Course> call(Student student) {
                return Observable.from(student.course);
            }

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

        // TODO: 2016/12/16 lift实现 integer对象转换 string

        observable.lift(new Observable.Operator<String, Integer>() {

            @Override
            public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                return new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        subscriber.onNext(integer + "=============");

                    }
                };
            }
        });


    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
