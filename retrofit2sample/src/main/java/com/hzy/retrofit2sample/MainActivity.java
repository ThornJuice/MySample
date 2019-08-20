package com.hzy.retrofit2sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hzy.retrofit2sample.bean.WeatherInfo;
import com.hzy.retrofit2sample.http.CustomResourceSubsciber;
import com.hzy.retrofit2sample.http.RetrofitManager;
import com.hzy.retrofit2sample.util.RxUtil;
import com.hzy.retrofit2sample.util.XLog;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public class MainActivity extends AppCompatActivity {
    private Unbinder mUnbinder;
    @BindView(R.id.getWeather)
    Button getWeather;
    @BindView(R.id.tv_Weather)
    TextView tv_Weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.getWeather})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.getWeather:
                getWeather();
                break;
        }
    }

    //tv_Weather.setText(weatherInfo.toString());
    private void getWeather() {
        RetrofitManager.getInstance().create().getWeatherInfo("101010100")
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        XLog.e("doOnSubscribe");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        XLog.e("doFinally");
                    }
                }).compose(RxUtil.<WeatherInfo>rxSchedulerTransformer())
                .subscribe(new CustomResourceSubsciber<WeatherInfo>() {
                    @Override
                    public void onNext(WeatherInfo weatherInfo) {
                        XLog.e("onNext");
                        tv_Weather.setText(weatherInfo.toString());
                    }

                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }
}
