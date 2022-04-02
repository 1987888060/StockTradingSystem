package jsu.per.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jsu.per.system.dao.AdminMapper;
import jsu.per.system.pojo.Admin;
import jsu.per.system.service.AdminService;
import jsu.per.system.service.AdminTokenService;
import jsu.per.system.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    private AdminTokenService adminTokenService;


    @Lazy
    public AdminServiceImpl(AdminTokenService service){
        this.adminTokenService  = service;
    }

    @Override
    public Admin getAdminBy(String adminname) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("adminname",adminname);

        return adminMapper.selectOne(queryWrapper);
    }

    @Override
    public Admin getAdminBy(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);

        return adminMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Admin> getUsersBy(String adminname) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("adminname",adminname);

        return adminMapper.selectList(queryWrapper);
    }

    @Override
    public List<Admin> getAllUser() {
        return adminMapper.selectList(null);
    }

    @Override
    public void addAdmin(Admin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public void updateAdmin(Admin admin) {
        adminMapper.updateById(admin);
    }

    @Override
    public void deleteAdmin(int adminid) {
        adminMapper.deleteById(adminid);
    }

    @Override
    public String login(int id) {
        String admin_id = ""+id;
        String token = null;
        try {
            token = TokenUtil.creatToken(admin_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        adminTokenService.updateToken(token,admin_id);
        return token;
    }

    @Override
    public void logout(String token) {
        adminTokenService.deleteToken(token);
    }
}
