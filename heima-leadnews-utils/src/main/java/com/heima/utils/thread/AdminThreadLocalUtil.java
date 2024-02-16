package com.heima.utils.thread;

import com.heima.model.admin.pojos.AdUser;

/**
 * 平台管理端线程本地工具类
 */
public class AdminThreadLocalUtil {

    public final static   ThreadLocal<AdUser> ADMIN_THREAD_LOCAL = new ThreadLocal<>();


    /**
     * 设置管理员
     */
    public  static void setAdmin(AdUser admin){
        ADMIN_THREAD_LOCAL.set(admin);
    }
    /**
     *
     * 获取管理员
     */
    public static AdUser getAdmin(){
        return ADMIN_THREAD_LOCAL.get();
    }

    /**
     *
     * 移除管理员
     */
    public static void clearAdmin(){
        ADMIN_THREAD_LOCAL.remove();
    }




}
