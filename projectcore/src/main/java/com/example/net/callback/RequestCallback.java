package com.example.net.callback;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallback implements Callback<String>{
    private static final String TAG = "RequestCallback";

    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final IError IERROR;
    private final IFailure IFAILURE;
    private static final Handler HANDLER = new Handler();

    public RequestCallback(IRequest irequest, ISuccess isuccess, IError ierror, IFailure ifailure) {
        IREQUEST = irequest;
        ISUCCESS = isuccess;
        IERROR = ierror;
        IFAILURE = ifailure;
    }

    @Override
    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
        Log.i(TAG, "onResponse.errorBody: " + response.errorBody());

        if(response.isSuccessful()){
            if(call.isExecuted()){
                if (ISUCCESS != null){
                    ISUCCESS.onSuccess(response.body());
                }
            }
        }else {
            if(IERROR != null){
                IERROR.onError(response.code(),response.message());
            }
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        Log.i(TAG, "onFailure: " + t);
        if(IFAILURE != null){
            IFAILURE.onFailure();
        }
        if(IREQUEST != null){
            IREQUEST.onRequestFinish();
        }
    }

}
