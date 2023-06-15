package com.javaee.lqsx.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.po.Returns;

import java.util.*;

/**
 * 用户Service层接口
 */

public interface ReturnsService extends IService<Returns> {

    //分页查询
    public PageInfo<Returns> findPageInfo(Integer pageIndex, Integer pageSize, Map mp);

    //添加
    public int addReturns(Returns returns);

    //删除
    public int deleteReturns(String id);

    //修改
    public int updateReturns(Returns returns);

    //根据ID查询
    public Returns findReturnsById(String id);

    //查询所有
    public List<Returns> getAll();

    //按照条件查询
    public List<Returns> queryFilter(Map mp);
}
