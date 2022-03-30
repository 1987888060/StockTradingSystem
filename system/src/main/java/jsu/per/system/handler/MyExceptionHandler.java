package jsu.per.system.handler;

import jsu.per.system.result.JsonResult;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public JsonResult<String> handleException(AuthorizationException e) {
        //e.printStackTrace();
        JsonResult<String> result = new JsonResult<>();
        result.setCode("400");
        //获取错误中中括号的内容
        String message = e.getMessage();
        String msg=message.substring(message.indexOf("[")+1,message.indexOf("]"));
        //判断是角色错误还是权限错误
        if (message.contains("role")) {
            result.setMsg("对不起，您没有" + msg + "角色");
        } else if (message.contains("permission")) {
            result.setMsg("对不起，您没有" + msg + "权限");
        } else {
            result.setMsg("对不起，您的权限有误");
        }
        return result;
    }
}