package jsu.per.system.utils;


import org.apache.shiro.crypto.hash.Md5Hash;

public class TokenUtil {
    public static String creatToken(String user_id) {
        String salt = "cg";
        String str = user_id + System.currentTimeMillis();
        Md5Hash md5Hash = new Md5Hash(str,salt);
        String token = new String(md5Hash.getBytes());
        return token;
    }
}
