package com.example.net;

import android.content.Context;

import com.example.net.callback.IError;
import com.example.net.callback.IFailure;
import com.example.net.callback.IRequest;
import com.example.net.callback.ISuccess;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RetrofitClientBuilder {

    private  String mUrl;
    private  Map<String,Object> mParams;
    private  Map<String,Object> mHeaders;
    private  String download_dir;
    private  String extension;
    private  String name;
    private  IRequest mIRequest;
    private  ISuccess mISuccess;
    private  IError mIError;
    private  IFailure mIFailure;
    private  RequestBody mRequestBody;
    private File file;
    private  Context context;

    public final RetrofitClientBuilder url(String mUrl){
        this.mUrl = mUrl;
        return this;
    }

    public final RetrofitClientBuilder downloadPath(String path){
        this.name = path;
        return this;
    }

    public final RetrofitClientBuilder downloadDir(String path){
        this.download_dir = path;
        return this;
    }

    public final RetrofitClientBuilder downloadExtension(String extension){
        this.extension = extension;
        return this;
    }

    public final RetrofitClientBuilder header(Map<String,Object> headers){
        this.mHeaders = headers;
        return this;
    }

    public final RetrofitClientBuilder header(String key,Object val){
        if(mHeaders == null){
            mHeaders = new HashMap<>();
        }
        this.mHeaders.put(key,val);
        return this;
    }

    public final RetrofitClientBuilder file(String file){
        this.file = new File(file);
        return this;
    }

    public final RetrofitClientBuilder file(File file){
        this.file = file;
        return this;
    }

    public final RetrofitClientBuilder params(Map<String,Object> mParams){
        this.mParams = mParams;
        return this;
    }

    public final RetrofitClientBuilder params(String key,Object val){
        if(mParams == null){
            mParams = new HashMap<>();
        }
        this.mParams.put(key,val);
        return this;
    }

    public final RetrofitClientBuilder raw(String raw){
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }

    public final RetrofitClientBuilder success(ISuccess success){
        this.mISuccess = success;
        return this;
    }

    public final RetrofitClientBuilder error(IError error){
        this.mIError = error;
        return this;
    }

    public final RetrofitClientBuilder failure(IFailure failure){
        this.mIFailure = failure;
        return this;
    }

    public final RetrofitClientBuilder request(IRequest request){
        this.mIRequest = request;
        return this;
    }


    private void check(){
        if(mParams == null){
            mParams = new HashMap<>();
        }
        if(mHeaders == null){
            mHeaders = new HashMap<>();
        }
    }

    public final RetrofitClient build(){
        check();
        return new RetrofitClient(mUrl,
                mParams,
                mHeaders,
                mIRequest,
                download_dir,
                extension,
                name,
                mISuccess,
                mIError,
                mIFailure,
                mRequestBody,
                file,
                context);
    }
}
