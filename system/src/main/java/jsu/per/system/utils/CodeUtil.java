package jsu.per.system.utils;

import java.util.Random;

public class CodeUtil {
    /**
     * 生成一个6位的验证码
     * @return
     */
    public synchronized static String  getCode() {
        String str = "";
        for(int i = 0;i<6;i++){
            str = str + (int)(Math.random()*10);
        }
        return str;
    }
}
