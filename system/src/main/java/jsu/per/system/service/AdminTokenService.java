package jsu.per.system.service;

public interface AdminTokenService {
    String isExistKey(String token,String str);

    String updateToken(String token,String admin_id);

    void deleteToken(String token);

    String addToken(String token,String admin_id);
}
