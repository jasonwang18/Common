package com.supcon.common.com_router.util;

/**
 * Created by wangshizhan on 2018/4/28.
 * Email:wangshizhan@supcon.com
 */

public enum RouteType {

    ACTIVITY(0,""),
    SERVICE(0,""),
    PROVIDER(0,""),
    BROADCAST(0,""),
    EVENTBUS(0,""),
    METHOD(0,""),
    CONTENT_PROVIDER(0,""),
    FRAGMENT(0,""),
    UNKNOWN(0,"");


    public static RouteType parse(String paramString)
    {
        for (RouteType localRouteType : values())
            if (localRouteType.getClassName().equals(paramString))
                return localRouteType;
        return UNKNOWN;
    }

    private int id;
    private String className;

    RouteType(int id, String className){
        this.id = id;
        this.className = className;
    }


    public String getClassName() {
        return className;
    }

    public int getId() {
        return id;
    }
}
