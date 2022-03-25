package jsu.per.system.service;

public interface UserTokenService {

    String isExistKey(String token,String str);

    String updateToken(String token,String user_id);

    void deleteToken(String token);

    String addToken(String token,String user_id);

}
