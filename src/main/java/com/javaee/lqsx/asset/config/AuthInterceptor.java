package com.javaee.lqsx.asset.config;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description: 拦截器
 **/
//登录拦截器
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        //判断有没有登录
        if(session.getAttribute("ad") != null){
            return true;
        }

        //不符合条件的给出提示信息，并转发到主页面
        request.setAttribute("msg", "您还没有登录，请先登录！");
        request.getRequestDispatcher("/gologin.jsp").forward(request, response);

        //返回true通过，返回false拦截
        return false;
    }
}
