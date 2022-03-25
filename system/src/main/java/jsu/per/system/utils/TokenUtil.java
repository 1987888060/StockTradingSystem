package jsu.per.system.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.security.MessageDigest;

@Slf4j
public class TokenUtil {
    public static String creatToken(String user_id) throws Exception {
        String str = user_id + System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();

        try {
            byte[] md5s = MessageDigest.getInstance("MD5").digest(str.getBytes("utf-8"));
            for (byte b : md5s) {
                stringBuilder.append(String.format("%02x", new Integer(b & 0xff)));
            }
        } catch (Exception e) {
            log.warn("md5初始化失败");
            throw new Exception("md5初始化失败");
        }
        return stringBuilder.toString();
    }


}
