package com.javaee.lqsx.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaee.lqsx.asset.po.Repair;
import org.apache.ibatis.annotations.Param;
import java.util.*;

/**
 * DAO层接口
 */

//资产维修信息功能接口
public interface RepairMapper extends BaseMapper<Repair>{

    //获取列表
    public List<Repair> getRepairList(Map mp);

    //获取总条数
    public Integer totalCount(Map mp);

    //添加
    public int addRepair(Repair repair);

    //删除
    public int deleteRepair(@Param("id") String id);

    //修改
    public int updateRepair(Repair repair);

    //根据ID查询
    public Repair findRepairById(@Param("id") String id);

    //查询所有
    public List<Repair> getAll();

    //按照条件查询
    public List<Repair> queryFilter(Map mp);

}
