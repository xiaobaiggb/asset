package com.javaee.lqsx.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.po.Recipients;

import java.util.*;

/**
 * 用户Service层接口
 */

public interface RecipientsService extends IService<Recipients> {

    //分页查询
    public PageInfo<Recipients> findPageInfo(Integer pageIndex, Integer pageSize, Map mp);

    //添加
    public int addRecipients(Recipients recipients);

    //删除
    public int deleteRecipients(String id);

    //修改
    public int updateRecipients(Recipients recipients);

    //根据ID查询
    public Recipients findRecipientsById(String id);

    //查询所有
    public List<Recipients> getAll();

    //按照条件查询
    public List<Recipients> queryFilter(Map mp);
}
