package com.javaee.lqsx.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaee.lqsx.asset.po.Admin;
import com.javaee.lqsx.asset.po.PageInfo;

import java.util.*;

/**
 * 用户Service层接口
 */

public interface AdminService extends IService<Admin> {

    //分页查询
    public PageInfo<Admin> findPageInfo(Integer pageIndex, Integer pageSize, Map mp);

    //添加
    public int addAdmin(Admin admin);

    //删除
    public int deleteAdmin(String id);

    //修改
    public int updateAdmin(Admin admin);

    //根据ID查询
    public Admin findAdminById(String id);

    //查询所有
    public List<Admin> getAll();

    //按照条件查询
    public List<Admin> queryFilter(Map mp);
}
