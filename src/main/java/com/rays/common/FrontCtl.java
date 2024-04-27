package com.rays.common;

import com.rays.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Component
public class FrontCtl extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        System.out.println("FrontCtl =>" + session.getId());
        String path = request.getServletPath();
        if (!path.startsWith("/Auth/")) {

            System.out.println("2222");

            if ((UserDTO) session.getAttribute("user") == null) {
                System.out.println("3333");
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
                response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                response.setHeader("Access-Control-Allow-Headers", "*");
                response.setHeader("Access-Control-Allow-Credentials", "true");
                PrintWriter out = response.getWriter();
                out.print("{\"success\":\"false\",\"error\":\"OOPS! Your session has been expired\"}");
                out.close();
                System.out.println("going to return false ");
                return false;
            }
            System.out.println("4444");
            return true;
        }
        System.out.println("5555");
        return false;


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("inside post handler");
        response.setHeader("Access-Control-Allow-Origin", "");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");

    }
}
