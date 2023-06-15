package com.javaee.lqsx.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaee.lqsx.asset.po.Manage;
import org.apache.ibatis.annotations.Param;
import java.util.*;

/**
 * DAO层接口
 */

//资产管理员功能接口
public interface ManageMapper extends BaseMapper<Manage>{

    //获取列表
    public List<Manage> getManageList(Map mp);

    //获取总条数
    public Integer totalCount(Map mp);

    //添加
    public int addManage(Manage manage);

    //删除
    public int deleteManage(@Param("id") String id);

    //修改
    public int updateManage(Manage manage);

    //根据ID查询
    public Manage findManageById(@Param("id") String id);

    //查询所有
    public List<Manage> getAll();

    //按照条件查询
    public List<Manage> queryFilter(Map mp);

}
