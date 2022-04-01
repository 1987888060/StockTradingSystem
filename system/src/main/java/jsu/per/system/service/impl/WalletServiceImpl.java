package jsu.per.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jsu.per.system.dao.UserMapper;
import jsu.per.system.dao.WalletMapper;
import jsu.per.system.pojo.Wallet;
import jsu.per.system.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletMapper walletMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public boolean saving(int userid,double money) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);

        Wallet wallet = walletMapper.selectOne(queryWrapper);
        wallet.setMoney(wallet.getMoney()+money);

        walletMapper.updateById(wallet);
        return true;
    }

    @Override
    public boolean remove(int userid,double money) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);

        Wallet wallet = walletMapper.selectOne(queryWrapper);
        if (wallet.getMoney()<money){//余额不足
            return false;
        }else{
            wallet.setMoney(wallet.getMoney()-money);
            walletMapper.updateById(wallet);
        }
        return true;
    }

    @Override
    public void buildWallet(int userid) {
        Wallet wallet = new Wallet();
        wallet.setUserid(userid);
        walletMapper.insert(wallet);
    }

    @Override
    public double getMoney(int userid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);

        Wallet wallet = walletMapper.selectOne(queryWrapper);

        return wallet.getMoney();
    }

    @Override
    public List<Wallet> selectAll() {
        return walletMapper.selectList(null);
    }
}
