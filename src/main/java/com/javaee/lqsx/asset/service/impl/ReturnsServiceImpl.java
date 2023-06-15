package com.javaee.lqsx.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaee.lqsx.asset.mapper.ReturnsMapper;
import com.javaee.lqsx.asset.util.RandomUtil;
import com.javaee.lqsx.asset.po.Returns;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.ReturnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import com.javaee.lqsx.asset.util.*;

/**
 * 用户Service接口实现类
 */

@Service("returnsService")
@Transactional
public class ReturnsServiceImpl  extends ServiceImpl<ReturnsMapper, Returns> implements ReturnsService {

    @Autowired
    private ReturnsMapper returnsMapper;


    //分页查询
    @Override
    public PageInfo<Returns> findPageInfo(Integer pageIndex, Integer pageSize,Map mp) {
        PageInfo<Returns> pi = new PageInfo<Returns>();
        pi.setPageIndex(pageIndex);
        pi.setPageSize(pageSize);
        mp.put("currentPage",(pi.getPageIndex()-1)*pi.getPageSize());
        mp.put("pageSize",pi.getPageSize());
        //获取总条数
        Integer totalCount = returnsMapper.totalCount(mp);
        if (totalCount>0){
            pi.setTotalCount(totalCount);
            //按条件查询
            List<Returns> returnsList =	returnsMapper.getReturnsList(mp);
            pi.setList(returnsList);
        }
        return pi;
    }


    //添加
    @Override
    public int addReturns(Returns returns) {
        returns.setId(RandomUtil.getRandomIdByUUID());
        return returnsMapper.addReturns(returns);
    }


    //根据id删除
    @Override
    public int deleteReturns(String id) {
        return returnsMapper.deleteReturns(id);
    }


    //修改信息
    @Override
    public int updateReturns(Returns returns) {
        return returnsMapper.updateReturns(returns);
    }

    //根据ID查询
    @Override
    public Returns findReturnsById (String id){
        Returns d = returnsMapper.findReturnsById(id);
        return  d;
    }

    //查询所有
    @Override
    public List<Returns> getAll(){
        List<Returns> returnsList = returnsMapper.getAll();
        return returnsList;
    }

    //按照条件查询
    @Override
    public List<Returns> queryFilter(Map mp){
        List<Returns> returnsList = returnsMapper.queryFilter(mp);
        return returnsList;
    }
}
