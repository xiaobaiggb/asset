package com.javaee.lqsx.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaee.lqsx.asset.po.Manage;
import com.javaee.lqsx.asset.po.PageInfo;

import java.util.*;

/**
 * 用户Service层接口
 */

public interface ManageService extends IService<Manage> {

    //分页查询
    public PageInfo<Manage> findPageInfo(Integer pageIndex, Integer pageSize, Map mp);

    //添加
    public int addManage(Manage manage);

    //删除
    public int deleteManage(String id);

    //修改
    public int updateManage(Manage manage);

    //根据ID查询
    public Manage findManageById(String id);

    //查询所有
    public List<Manage> getAll();

    //按照条件查询
    public List<Manage> queryFilter(Map mp);
}
