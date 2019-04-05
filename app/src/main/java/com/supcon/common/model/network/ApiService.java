package com.supcon.common.model.network;

import com.app.annotation.apt.ApiFactory;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

@ApiFactory(name = "HttpClient")
public interface ApiService {
    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     * @param map      默认参数
     * @return
     */
    @GET("/cas/mobile/logon")
    Flowable<String> login(@Query("username") String username, @Query("password") String password, @QueryMap Map<String, Object> map);

    /**
     * 登陆
     * @return
     */
    @GET("/cas/mobile/logon")
    Flowable<String> logout();
}