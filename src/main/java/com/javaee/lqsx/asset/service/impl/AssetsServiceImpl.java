package com.javaee.lqsx.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaee.lqsx.asset.mapper.AssetsMapper;
import com.javaee.lqsx.asset.util.RandomUtil;
import com.javaee.lqsx.asset.po.Assets;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.AssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import com.javaee.lqsx.asset.util.*;

/**
 * 用户Service接口实现类
 */

@Service("assetsService")
@Transactional
public class AssetsServiceImpl  extends ServiceImpl<AssetsMapper, Assets> implements AssetsService {

    @Autowired
    private AssetsMapper assetsMapper;


    //分页查询
    @Override
    public PageInfo<Assets> findPageInfo(Integer pageIndex, Integer pageSize,Map mp) {
        PageInfo<Assets> pi = new PageInfo<Assets>();
        pi.setPageIndex(pageIndex);
        pi.setPageSize(pageSize);
        mp.put("currentPage",(pi.getPageIndex()-1)*pi.getPageSize());
        mp.put("pageSize",pi.getPageSize());
        //获取总条数
        Integer totalCount = assetsMapper.totalCount(mp);
        System.out.println(totalCount);
        if (totalCount>0){
            pi.setTotalCount(totalCount);
            //按条件查询
            List<Assets> assetsList = assetsMapper.getAssetsList(mp);
            System.out.println(assetsList);
            pi.setList(assetsList);
        }
        return pi;
    }


    //添加
    @Override
    public int addAssets(Assets assets) {
        assets.setId(RandomUtil.getRandomIdByUUID());
        return assetsMapper.addAssets(assets);
    }


    //根据id删除
    @Override
    public int deleteAssets(String id) {
        return assetsMapper.deleteAssets(id);
    }


    //修改信息
    @Override
    public int updateAssets(Assets assets) {
        return assetsMapper.updateAssets(assets);
    }

    //根据ID查询
    @Override
    public Assets findAssetsById (String id){
        Assets d = assetsMapper.findAssetsById(id);
        return  d;
    }

    //查询所有
    @Override
    public List<Assets> getAll(){
        List<Assets> assetsList = assetsMapper.getAll();
        return assetsList;
    }

    //按照条件查询
    @Override
    public List<Assets> queryFilter(Map mp){
        List<Assets> assetsList = assetsMapper.queryFilter(mp);
        return assetsList;
    }
}
