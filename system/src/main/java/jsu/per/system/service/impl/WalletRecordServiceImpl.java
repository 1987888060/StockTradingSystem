package jsu.per.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jsu.per.system.dao.WalletMapper;
import jsu.per.system.dao.WalletRecordMapper;
import jsu.per.system.pojo.WalletRecord;
import jsu.per.system.service.WalletRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletRecordServiceImpl implements WalletRecordService {
    @Autowired
    WalletRecordMapper walletRecordMapper;

    @Override
    public void saving(WalletRecord record) {
        walletRecordMapper.insert(record);
    }

    @Override
    public List<WalletRecord> readByUserid(int userid) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);

        List list = walletRecordMapper.selectList(queryWrapper);
        return list;

    }

    @Override
    public List<WalletRecord> selectAll() {
        List<WalletRecord> walletRecords = walletRecordMapper.selectList(null);
        return walletRecords;
    }
}
