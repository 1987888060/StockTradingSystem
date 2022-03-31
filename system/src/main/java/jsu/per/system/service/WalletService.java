package jsu.per.system.service;

public interface WalletService {
    /**
     * 存钱
     */
    boolean saving(int userid,double money);

    /**
     * 取钱
     */
    boolean remove(int userid,double money);

    /**
     * 建立钱包
     */
    void buildWallet(int userid);

    double getMoney(int userid);
}
