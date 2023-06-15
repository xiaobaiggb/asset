package com.javaee.lqsx.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaee.lqsx.asset.po.User;
import org.apache.ibatis.annotations.Param;
import java.util.*;

/**
 * DAO层接口
 */

//员工信息功能接口
public interface UserMapper extends BaseMapper<User>{

    //获取列表
    public List<User> getUserList(Map mp);

    //获取总条数
    public Integer totalCount(Map mp);

    //添加
    public int addUser(User user);

    //删除
    public int deleteUser(@Param("id") String id);

    //修改
    public int updateUser(User user);

    //根据ID查询
    public User findUserById(@Param("id") String id);

    //查询所有
    public List<User> getAll();

    //按照条件查询
    public List<User> queryFilter(Map mp);

}
