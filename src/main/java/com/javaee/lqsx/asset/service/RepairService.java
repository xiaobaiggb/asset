package com.javaee.lqsx.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.po.Repair;

import java.util.*;

/**
 * 用户Service层接口
 */

public interface RepairService extends IService<Repair> {

    //分页查询
    public PageInfo<Repair> findPageInfo(Integer pageIndex, Integer pageSize, Map mp);

    //添加
    public int addRepair(Repair repair);

    //删除
    public int deleteRepair(String id);

    //修改
    public int updateRepair(Repair repair);

    //根据ID查询
    public Repair findRepairById(String id);

    //查询所有
    public List<Repair> getAll();

    //按照条件查询
    public List<Repair> queryFilter(Map mp);
}
