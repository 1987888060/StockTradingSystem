package jsu.per.system.service;

import jsu.per.system.pojo.User;
import jsu.per.system.pojo.UserToken;

public interface UserTokenService {

    String addToken(String user_id,String token);

    UserToken getToken(String user_id);

    UserToken updateToken(UserToken token);

    void deleteToken(String user_id);

    boolean isExistKey(String user_id);
}
