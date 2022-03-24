package jsu.per.system.result;

import lombok.Data;

@Data
public class JsonResult<T> {

    private T data;
    private String code;
    private String msg;

    public JsonResult(){
        code="0";
        msg="操作成功";
    }

    public JsonResult(T data) {
        this.data = data;
    }

    public JsonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResult(T data, String code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
}
