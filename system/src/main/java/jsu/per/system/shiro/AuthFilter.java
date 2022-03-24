package jsu.per.system.shiro;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jsu.per.system.result.JsonResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends AuthenticatingFilter {
    /**
     * 生成自定义token
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        //从header中获取token
        HttpServletRequest rq = (HttpServletRequest) request;
        String token = rq.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = rq.getParameter("token");
        }

        System.out.println("后端获取前端headers或者参数处的token="+token);

        return new AuthToken(token);
    }

    /**
     * 所有请求全部拒绝访问
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return false;
    }

    /**
     *  isAccessAllowed 返回 false 之后执行的，即访问拒绝的逻辑
     * 拒绝访问的请求，会调用onAccessDenied方法，onAccessDenied方法先获取 token，再调用executeLogin方法
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回
        //从header中获取token
        HttpServletRequest rq = (HttpServletRequest) request;
        String token = rq.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = rq.getParameter("token");
        }
        System.out.println("前端请求token="+token);
        if (StringUtils.isBlank(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            String orgin = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Origin");
            httpResponse.setHeader("Access-Control-Allow-Origin", orgin);
            httpResponse.setCharacterEncoding("UTF-8");
            JsonResult jsonResult = new JsonResult();
            jsonResult.setCode("403");
            jsonResult.setMsg("请先登录");
            String json = new ObjectMapper().writeValueAsString(jsonResult);
            httpResponse.getWriter().print(json);
            return false;
        }
        return executeLogin(request, response);
    }

    /**
     * token失效时候调用
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        String orgin = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Origin");
        httpResponse.setHeader("Access-Control-Allow-Origin", orgin);
        httpResponse.setCharacterEncoding("UTF-8");
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            JsonResult jsonResult = new JsonResult();
            jsonResult.setCode("403");
            jsonResult.setMsg("登录凭证已失效，请重新登录");
            String json = new ObjectMapper().writeValueAsString(jsonResult);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
        }
        return false;
    }
}
