package com.example.net;

import android.nfc.Tag;
import android.util.Log;

import com.example.net.factory.ScalarsConverterFactory;
import com.example.net.rx.RxRetrofitService;
import com.example.projectcore.ConfigType;
import com.example.projectcore.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitCreator {

    private static final class RetrofitHolder{
        private static final String BASE_URL = (String) Config.getConfigurations(ConfigType.API_HOST.name());
        private static final Retrofit RETROFIT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class OKHttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = Config.getConfigurations(ConfigType.INTERCEPTOR);
        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        private static OkHttpClient.Builder addInterceptor(){
            if(INTERCEPTORS !=null || INTERCEPTORS !=null){
                for(Interceptor interceptor:INTERCEPTORS){
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

    }

    private static final class RetrofitServiceHolder{
        private static final RetrofitService RETROFIT_SERVICE =
                RetrofitHolder.RETROFIT.create(RetrofitService.class);
    }

    public static RetrofitService getRetrofitService(){
        return RetrofitServiceHolder.RETROFIT_SERVICE;
    }

    private static final class RxRetrofitServiceHolder{
        private static final RxRetrofitService RETROFIT_SERVICE =
                RetrofitHolder.RETROFIT.create(RxRetrofitService.class);
    }

    public static RxRetrofitService getRxRetrofitService(){
        return RxRetrofitServiceHolder.RETROFIT_SERVICE;
    }

}
