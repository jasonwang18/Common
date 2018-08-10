package com.supcon.common.com_router.util;

import com.app.annotation.apt.Route;

import java.util.Map;

/**
 * Created by wangshizhan on 2018/4/28.
 * Email:wangshizhan@supcon.com
 */

public class RouteMeta
{
    private Class<?> destination;
    private int extra;
    private String group;
    private Map<String, Integer> paramsType;
    private String path;
    private int priority = -1;
    private RouteType type;

    public RouteMeta()
    {
    }

    public RouteMeta(Route paramRoute, Class<?> paramClass, RouteType paramRouteType)
    {
        this(paramRouteType, paramClass, paramRoute.path(), paramRoute.group(), null, paramRoute.priority(), paramRoute.extras());
    }

    public RouteMeta(Route paramRoute, RouteType paramRouteType, Map<String, Integer> paramMap)
    {
        this(paramRouteType, null, paramRoute.path(), paramRoute.group(), paramMap, paramRoute.priority(), paramRoute.extras());
    }

    public RouteMeta(RouteType paramRouteType,  Class<?> paramClass, String paramString1, String paramString2, Map<String, Integer> paramMap, int paramInt1, int paramInt2)
    {
        this.type = paramRouteType;
        this.destination = paramClass;
        this.path = paramString1;
        this.group = paramString2;
        this.paramsType = paramMap;
        this.priority = paramInt1;
        this.extra = paramInt2;
    }

    public static RouteMeta build(RouteType paramRouteType, Class<?> paramClass, String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
        return new RouteMeta(paramRouteType, paramClass, paramString1, paramString2, null, paramInt1, paramInt2);
    }

    public static RouteMeta build(RouteType paramRouteType, Class<?> paramClass, String paramString1, String paramString2, Map<String, Integer> paramMap, int paramInt1, int paramInt2)
    {
        return new RouteMeta(paramRouteType, paramClass, paramString1, paramString2, paramMap, paramInt1, paramInt2);
    }

    public Class<?> getDestination()
    {
        return this.destination;
    }

    public int getExtra()
    {
        return this.extra;
    }

    public String getGroup()
    {
        return this.group;
    }

    public Map<String, Integer> getParamsType()
    {
        return this.paramsType;
    }

    public String getPath()
    {
        return this.path;
    }

    public int getPriority()
    {
        return this.priority;
    }


    public RouteType getType()
    {
        return this.type;
    }

    public RouteMeta setDestination(Class<?> paramClass)
    {
        this.destination = paramClass;
        return this;
    }

    public RouteMeta setExtra(int paramInt)
    {
        this.extra = paramInt;
        return this;
    }

    public RouteMeta setGroup(String paramString)
    {
        this.group = paramString;
        return this;
    }

    public RouteMeta setParamsType(Map<String, Integer> paramMap)
    {
        this.paramsType = paramMap;
        return this;
    }

    public RouteMeta setPath(String paramString)
    {
        this.path = paramString;
        return this;
    }

    public RouteMeta setPriority(int paramInt)
    {
        this.priority = paramInt;
        return this;
    }


    public RouteMeta setType(RouteType paramRouteType)
    {
        this.type = paramRouteType;
        return this;
    }

    public String toString()
    {
        return "RouteMeta{type=" + this.type + ", destination=" + this.destination + ", path='" + this.path + '\'' + ", group='" + this.group + '\'' + ", priority=" + this.priority + ", extra=" + this.extra + '}';
    }
}
