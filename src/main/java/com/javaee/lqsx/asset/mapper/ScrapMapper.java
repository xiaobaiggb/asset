package com.javaee.lqsx.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaee.lqsx.asset.po.Scrap;
import org.apache.ibatis.annotations.Param;
import java.util.*;

/**
 * DAO层接口
 */

//资产报废信息功能接口
public interface ScrapMapper extends BaseMapper<Scrap>{

    //获取列表
    public List<Scrap> getScrapList(Map mp);

    //获取总条数
    public Integer totalCount(Map mp);

    //添加
    public int addScrap(Scrap scrap);

    //删除
    public int deleteScrap(@Param("id") String id);

    //修改
    public int updateScrap(Scrap scrap);

    //根据ID查询
    public Scrap findScrapById(@Param("id") String id);

    //查询所有
    public List<Scrap> getAll();

    //按照条件查询
    public List<Scrap> queryFilter(Map mp);

}
