package com.javaee.lqsx.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.po.Scrap;

import java.util.*;

/**
 * 用户Service层接口
 */

public interface ScrapService extends IService<Scrap> {

    //分页查询
    public PageInfo<Scrap> findPageInfo(Integer pageIndex, Integer pageSize, Map mp);

    //添加
    public int addScrap(Scrap scrap);

    //删除
    public int deleteScrap(String id);

    //修改
    public int updateScrap(Scrap scrap);

    //根据ID查询
    public Scrap findScrapById(String id);

    //查询所有
    public List<Scrap> getAll();

    //按照条件查询
    public List<Scrap> queryFilter(Map mp);
}
