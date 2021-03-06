package com.example.net;

import android.content.Context;

import com.example.net.callback.IError;
import com.example.net.callback.IFailure;
import com.example.net.callback.IRequest;
import com.example.net.callback.ISuccess;
import com.example.net.callback.RequestCallback;
import com.example.net.download.DownloadHandler;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RetrofitClient {

    private final String URL;
    private final Map<String,Object> PARAMS;
    private final Map<String,Object> HEADERS;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final RequestBody BODY;
    private final File FILE;
    private final Context CONTEXT;

    RetrofitClient(String url, Map<String, Object> params,
                   Map<String, Object> headers,
                   IRequest irequest,
                   String download_dir,
                   String extension,
                   String name,
                   ISuccess isuccess,
                   IError ierror,
                   IFailure iFailure,
                   RequestBody body,
                   File file,
                   Context context) {
        URL = url;
        PARAMS = params;
        HEADERS = headers;
        REQUEST = irequest;
        DOWNLOAD_DIR = download_dir;
        EXTENSION = extension;
        NAME = name;
        SUCCESS = isuccess;
        ERROR = ierror;
        FAILURE = iFailure;
        BODY = body;
        FILE = file;
        CONTEXT = context;
    }

    public static RetrofitClientBuilder builder(){
        return new RetrofitClientBuilder();
    }

    private void request(HttpMethod method){
        final RetrofitService service = RetrofitCreator.getRetrofitService();
        Call<String> call= null;

        if(REQUEST != null){
            REQUEST.onRequestStart();
        }

        switch (method){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            case UPLOAD:
               final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
               final MultipartBody.Part body = MultipartBody.Part.createFormData("file",FILE.getName());
               call = RetrofitCreator.getRetrofitService().upload(URL, body);
               break;
        }

        if(call !=null){
            call.enqueue(getRequestCallback());

        }

    }

    private Callback<String> getRequestCallback(){
        return new RequestCallback(
                REQUEST,
                SUCCESS,
                ERROR,
                FAILURE
        );
    }

    public final void get(){
        request((HttpMethod.GET));
    }

    public final void post(){
        if(BODY == null){
            request((HttpMethod.POST));
        }else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void delete(){
        request((HttpMethod.DELETE));
    }

    public final void put(){
        if(BODY == null){
            request((HttpMethod.PUT));
        }else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void upload(){
        request(HttpMethod.UPLOAD);
    }

    public final void download(){
        new DownloadHandler(URL,PARAMS,REQUEST,DOWNLOAD_DIR,EXTENSION,NAME,SUCCESS,ERROR,FAILURE);
    }

}
