package com.javaee.lqsx.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaee.lqsx.asset.util.RandomUtil;
import com.javaee.lqsx.asset.mapper.ManageMapper;
import com.javaee.lqsx.asset.po.Manage;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import com.javaee.lqsx.asset.util.*;

/**
 * 用户Service接口实现类
 */

@Service("manageService")
@Transactional
public class ManageServiceImpl  extends ServiceImpl<ManageMapper, Manage> implements ManageService {

    @Autowired
    private ManageMapper manageMapper;


    //分页查询
    @Override
    public PageInfo<Manage> findPageInfo(Integer pageIndex, Integer pageSize,Map mp) {
        PageInfo<Manage> pi = new PageInfo<Manage>();
        pi.setPageIndex(pageIndex);
        pi.setPageSize(pageSize);
        mp.put("currentPage",(pi.getPageIndex()-1)*pi.getPageSize());
        mp.put("pageSize",pi.getPageSize());
        //获取总条数
        Integer totalCount = manageMapper.totalCount(mp);
        if (totalCount>0){
            pi.setTotalCount(totalCount);
            //按条件查询
            List<Manage> manageList =	manageMapper.getManageList(mp);
            pi.setList(manageList);
        }
        return pi;
    }


    //添加
    @Override
    public int addManage(Manage manage) {
        manage.setId(RandomUtil.getRandomIdByUUID());
        return manageMapper.addManage(manage);
    }


    //根据id删除
    @Override
    public int deleteManage(String id) {
        return manageMapper.deleteManage(id);
    }


    //修改信息
    @Override
    public int updateManage(Manage manage) {
        return manageMapper.updateManage(manage);
    }

    //根据ID查询
    @Override
    public Manage findManageById (String id){
        Manage d = manageMapper.findManageById(id);
        return  d;
    }

    //查询所有
    @Override
    public List<Manage> getAll(){
        List<Manage> manageList = manageMapper.getAll();
        return manageList;
    }

    //按照条件查询
    @Override
    public List<Manage> queryFilter(Map mp){
        List<Manage> manageList = manageMapper.queryFilter(mp);
        return manageList;
    }
}
