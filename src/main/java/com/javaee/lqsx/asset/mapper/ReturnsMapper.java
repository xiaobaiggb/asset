package com.javaee.lqsx.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaee.lqsx.asset.po.Returns;
import org.apache.ibatis.annotations.Param;
import java.util.*;

/**
 * DAO层接口
 */

//资产归还信息功能接口
public interface ReturnsMapper extends BaseMapper<Returns>{

    //获取列表
    public List<Returns> getReturnsList(Map mp);

    //获取总条数
    public Integer totalCount(Map mp);

    //添加
    public int addReturns(Returns returns);

    //删除
    public int deleteReturns(@Param("id") String id);

    //修改
    public int updateReturns(Returns returns);

    //根据ID查询
    public Returns findReturnsById(@Param("id") String id);

    //查询所有
    public List<Returns> getAll();

    //按照条件查询
    public List<Returns> queryFilter(Map mp);

}
