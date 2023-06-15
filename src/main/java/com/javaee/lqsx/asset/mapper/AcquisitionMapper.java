package com.javaee.lqsx.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaee.lqsx.asset.po.Acquisition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.*;

/**
 * DAO层接口
 */

//资产购置功能接口
@Mapper
public interface AcquisitionMapper extends BaseMapper<Acquisition>{

    //获取列表
    public List<Acquisition> getAcquisitionList(Map mp);

    //获取总条数
    public Integer totalCount(Map mp);

    //添加
    public int addAcquisition(Acquisition acquisition);

    //删除
    public int deleteAcquisition(@Param("id") String id);

    //修改
    public int updateAcquisition(Acquisition acquisition);

    //根据ID查询
    public Acquisition findAcquisitionById(@Param("id") String id);

    //查询所有
    public List<Acquisition> getAll();

    //按照条件查询
    public List<Acquisition> queryFilter(Map mp);

}
