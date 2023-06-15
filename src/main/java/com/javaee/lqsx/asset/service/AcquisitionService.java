package com.javaee.lqsx.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaee.lqsx.asset.po.Acquisition;
import com.javaee.lqsx.asset.po.PageInfo;
import java.util.*;

/**
 * 用户Service层接口
 */

public interface AcquisitionService extends IService<Acquisition> {

    //分页查询
    public PageInfo<Acquisition> findPageInfo(Integer pageIndex, Integer pageSize,Map mp);

    //添加
    public int addAcquisition(Acquisition acquisition);

    //删除
    public int deleteAcquisition(String id);

    //修改
    public int updateAcquisition(Acquisition acquisition);

    //根据ID查询
    public Acquisition findAcquisitionById(String id);

    //查询所有
    public List<Acquisition> getAll();

    //按照条件查询
    public List<Acquisition> queryFilter(Map mp);
}
