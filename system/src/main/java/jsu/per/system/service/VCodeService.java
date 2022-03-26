package jsu.per.system.service;

/**
 * 验证码存储redis
 */
public interface VCodeService {
    /**
     * 更新
     * @param email
     * @param code
     * @return
     */
    String updateCode(String email,String code);

    /**
     * 查询和添加
     * @param email
     * @param code
     * @return
     */
    String addCode(String email,String code);
}
