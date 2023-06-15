package com.javaee.lqsx.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaee.lqsx.asset.mapper.UserMapper;
import com.javaee.lqsx.asset.util.RandomUtil;
import com.javaee.lqsx.asset.po.User;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import com.javaee.lqsx.asset.util.*;

/**
 * 用户Service接口实现类
 */

@Service("userService")
@Transactional
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    //分页查询
    @Override
    public PageInfo<User> findPageInfo(Integer pageIndex, Integer pageSize,Map mp) {
        PageInfo<User> pi = new PageInfo<User>();
        pi.setPageIndex(pageIndex);
        pi.setPageSize(pageSize);
        mp.put("currentPage",(pi.getPageIndex()-1)*pi.getPageSize());
        mp.put("pageSize",pi.getPageSize());
        //获取总条数
        Integer totalCount = userMapper.totalCount(mp);
        if (totalCount>0){
            pi.setTotalCount(totalCount);
            //按条件查询
            List<User> userList = userMapper.getUserList(mp);
            pi.setList(userList);
        }
        return pi;
    }


    //添加
    @Override
    public int addUser(User user) {
        user.setId(RandomUtil.getRandomIdByUUID());
        return userMapper.addUser(user);
    }


    //根据id删除
    @Override
    public int deleteUser(String id) {
        return userMapper.deleteUser(id);
    }


    //修改信息
    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    //根据ID查询
    @Override
    public User findUserById (String id){
        User d = userMapper.findUserById(id);
        return  d;
    }

    //查询所有
    @Override
    public List<User> getAll(){
        List<User> userList = userMapper.getAll();
        return userList;
    }

    //按照条件查询
    @Override
    public List<User> queryFilter(Map mp){
        List<User> userList = userMapper.queryFilter(mp);
        return userList;
    }
}
