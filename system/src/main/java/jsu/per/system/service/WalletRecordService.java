package jsu.per.system.service;

import jsu.per.system.pojo.WalletRecord;

import java.util.List;

public interface WalletRecordService {

    void saving(WalletRecord record);

    List<WalletRecord> readByUserid(int userid);

    List<WalletRecord> selectAll();
}
