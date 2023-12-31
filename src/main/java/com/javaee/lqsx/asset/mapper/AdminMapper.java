package com.javaee.lqsx.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaee.lqsx.asset.po.Admin;
import org.apache.ibatis.annotations.Param;
import java.util.*;

/**
 * DAO层接口
 */

//管理员功能接口
public interface AdminMapper extends BaseMapper<Admin>{

    //获取列表
    public List<Admin> getAdminList(Map mp);

    //获取总条数
    public Integer totalCount(Map mp);

    //添加
    public int addAdmin(Admin admin);

    //删除
    public int deleteAdmin(@Param("id") String id);

    //修改
    public int updateAdmin(Admin admin);

    //根据ID查询
    public Admin findAdminById(@Param("id") String id);

    //查询所有
    public List<Admin> getAll();

    //按照条件查询
    public List<Admin> queryFilter(Map mp);

}
