package com.heima.admin.interceptor;

import com.heima.model.admin.pojos.AdUser;
import com.heima.utils.thread.AdminThreadLocalUtil;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
@Slf4j
public class AdminTokenInterceptor  implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取header中的管理员id
        String adminId = request.getHeader("adminId");
        Optional<String> optional = Optional.ofNullable(adminId);
        if(optional.isPresent()){
            //把管理员id存入threadLocal里面
            AdUser adUser  = new AdUser();
            adUser.setId(Integer.valueOf(adminId));
            AdminThreadLocalUtil.setAdmin(adUser);
            log.info("保存管理员id："+adminId+"到线程中");
        }
         return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AdminThreadLocalUtil.clearAdmin();
        log.info("移除管理员id");
    }
}
