package com.javaee.lqsx.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaee.lqsx.asset.po.Deprecition;
import com.javaee.lqsx.asset.po.PageInfo;

import java.util.*;

/**
 * 用户Service层接口
 */

public interface DeprecitionService extends IService<Deprecition> {

    //分页查询
    public PageInfo<Deprecition> findPageInfo(Integer pageIndex, Integer pageSize, Map mp);

    //添加
    public int addDeprecition(Deprecition deprecition);

    //删除
    public int deleteDeprecition(String id);

    //修改
    public int updateDeprecition(Deprecition deprecition);

    //根据ID查询
    public Deprecition findDeprecitionById(String id);

    //查询所有
    public List<Deprecition> getAll();

    //按照条件查询
    public List<Deprecition> queryFilter(Map mp);
}
