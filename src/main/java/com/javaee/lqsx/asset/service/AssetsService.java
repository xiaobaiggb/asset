package com.javaee.lqsx.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaee.lqsx.asset.po.Assets;
import com.javaee.lqsx.asset.po.PageInfo;
import java.util.*;

/**
 * 用户Service层接口
 */

public interface AssetsService extends IService<Assets> {

    //分页查询
    public PageInfo<Assets> findPageInfo(Integer pageIndex, Integer pageSize,Map mp);

    //添加
    public int addAssets(Assets assets);

    //删除
    public int deleteAssets(String id);

    //修改
    public int updateAssets(Assets assets);

    //根据ID查询
    public Assets findAssetsById(String id);

    //查询所有
    public List<Assets> getAll();

    //按照条件查询
    public List<Assets> queryFilter(Map mp);
}
