package com.javaee.lqsx.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaee.lqsx.asset.mapper.ScrapMapper;
import com.javaee.lqsx.asset.util.RandomUtil;
import com.javaee.lqsx.asset.po.Scrap;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import com.javaee.lqsx.asset.util.*;

/**
 * 用户Service接口实现类
 */

@Service("scrapService")
@Transactional
public class ScrapServiceImpl  extends ServiceImpl<ScrapMapper, Scrap> implements ScrapService {

    @Autowired
    private ScrapMapper scrapMapper;


    //分页查询
    @Override
    public PageInfo<Scrap> findPageInfo(Integer pageIndex, Integer pageSize,Map mp) {
        PageInfo<Scrap> pi = new PageInfo<Scrap>();
        pi.setPageIndex(pageIndex);
        pi.setPageSize(pageSize);
        mp.put("currentPage",(pi.getPageIndex()-1)*pi.getPageSize());
        mp.put("pageSize",pi.getPageSize());
        //获取总条数
        Integer totalCount = scrapMapper.totalCount(mp);
        if (totalCount>0){
            pi.setTotalCount(totalCount);
            //按条件查询
            List<Scrap> scrapList =	scrapMapper.getScrapList(mp);
            pi.setList(scrapList);
        }
        return pi;
    }


    //添加
    @Override
    public int addScrap(Scrap scrap) {
        scrap.setId(RandomUtil.getRandomIdByUUID());
        return scrapMapper.addScrap(scrap);
    }


    //根据id删除
    @Override
    public int deleteScrap(String id) {
        return scrapMapper.deleteScrap(id);
    }


    //修改信息
    @Override
    public int updateScrap(Scrap scrap) {
        return scrapMapper.updateScrap(scrap);
    }

    //根据ID查询
    @Override
    public Scrap findScrapById (String id){
        Scrap d = scrapMapper.findScrapById(id);
        return  d;
    }

    //查询所有
    @Override
    public List<Scrap> getAll(){
        List<Scrap> scrapList = scrapMapper.getAll();
        return scrapList;
    }

    //按照条件查询
    @Override
    public List<Scrap> queryFilter(Map mp){
        List<Scrap> scrapList = scrapMapper.queryFilter(mp);
        return scrapList;
    }
}
