package com.javaee.lqsx.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaee.lqsx.asset.mapper.AdminMapper;
import com.javaee.lqsx.asset.util.RandomUtil;
import com.javaee.lqsx.asset.po.Admin;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import com.javaee.lqsx.asset.util.*;

/**
 * 用户Service接口实现类
 */

@Service("adminService")
@Transactional
public class AdminServiceImpl  extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;


    //分页查询
    @Override
    public PageInfo<Admin> findPageInfo(Integer pageIndex, Integer pageSize,Map mp) {
        PageInfo<Admin> pi = new PageInfo<Admin>();
        pi.setPageIndex(pageIndex);
        pi.setPageSize(pageSize);
        mp.put("currentPage",(pi.getPageIndex()-1)*pi.getPageSize());
        mp.put("pageSize",pi.getPageSize());
        //获取总条数
        Integer totalCount = adminMapper.totalCount(mp);
        if (totalCount>0){
            pi.setTotalCount(totalCount);
            //按条件查询
            List<Admin> adminList =	adminMapper.getAdminList(mp);
            pi.setList(adminList);
        }
        return pi;
    }


    //添加
    @Override
    public int addAdmin(Admin admin) {
        admin.setId(RandomUtil.getRandomIdByUUID());
        return adminMapper.addAdmin(admin);
    }


    //根据id删除
    @Override
    public int deleteAdmin(String id) {
        return adminMapper.deleteAdmin(id);
    }


    //修改信息
    @Override
    public int updateAdmin(Admin admin) {
        return adminMapper.updateAdmin(admin);
    }

    //根据ID查询
    @Override
    public Admin findAdminById (String id){
        Admin d = adminMapper.findAdminById(id);
        return  d;
    }

    //查询所有
    @Override
    public List<Admin> getAll(){
        List<Admin> adminList = adminMapper.getAll();
        return adminList;
    }

    //按照条件查询
    @Override
    public List<Admin> queryFilter(Map mp){
        List<Admin> adminList = adminMapper.queryFilter(mp);
        return adminList;
    }
}
