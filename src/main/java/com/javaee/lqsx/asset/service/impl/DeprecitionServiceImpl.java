package com.javaee.lqsx.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaee.lqsx.asset.util.RandomUtil;
import com.javaee.lqsx.asset.mapper.DeprecitionMapper;
import com.javaee.lqsx.asset.po.Deprecition;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.DeprecitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import com.javaee.lqsx.asset.util.*;

/**
 * 用户Service接口实现类
 */

@Service("deprecitionService")
@Transactional
public class DeprecitionServiceImpl  extends ServiceImpl<DeprecitionMapper, Deprecition> implements DeprecitionService {

    @Autowired
    private DeprecitionMapper deprecitionMapper;


    //分页查询
    @Override
    public PageInfo<Deprecition> findPageInfo(Integer pageIndex, Integer pageSize,Map mp) {
        PageInfo<Deprecition> pi = new PageInfo<Deprecition>();
        pi.setPageIndex(pageIndex);
        pi.setPageSize(pageSize);
        mp.put("currentPage",(pi.getPageIndex()-1)*pi.getPageSize());
        mp.put("pageSize",pi.getPageSize());
        //获取总条数
        Integer totalCount = deprecitionMapper.totalCount(mp);
        if (totalCount>0){
            pi.setTotalCount(totalCount);
            //按条件查询
            List<Deprecition> deprecitionList =	deprecitionMapper.getDeprecitionList(mp);
            pi.setList(deprecitionList);
        }
        return pi;
    }


    //添加
    @Override
    public int addDeprecition(Deprecition deprecition) {
        deprecition.setId(RandomUtil.getRandomIdByUUID());
        return deprecitionMapper.addDeprecition(deprecition);
    }


    //根据id删除
    @Override
    public int deleteDeprecition(String id) {
        return deprecitionMapper.deleteDeprecition(id);
    }


    //修改信息
    @Override
    public int updateDeprecition(Deprecition deprecition) {
        return deprecitionMapper.updateDeprecition(deprecition);
    }

    //根据ID查询
    @Override
    public Deprecition findDeprecitionById (String id){
        Deprecition d = deprecitionMapper.findDeprecitionById(id);
        return  d;
    }

    //查询所有
    @Override
    public List<Deprecition> getAll(){
        List<Deprecition> deprecitionList = deprecitionMapper.getAll();
        return deprecitionList;
    }

    //按照条件查询
    @Override
    public List<Deprecition> queryFilter(Map mp){
        List<Deprecition> deprecitionList = deprecitionMapper.queryFilter(mp);
        return deprecitionList;
    }
}
