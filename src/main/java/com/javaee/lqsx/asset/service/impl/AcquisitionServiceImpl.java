package com.javaee.lqsx.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaee.lqsx.asset.mapper.AcquisitionMapper;
import com.javaee.lqsx.asset.po.Acquisition;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.AcquisitionService;
import com.javaee.lqsx.asset.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import com.javaee.lqsx.asset.util.*;

/**
 * 用户Service接口实现类
 */

@Service("acquisitionService")
@Transactional
public class AcquisitionServiceImpl  extends ServiceImpl<AcquisitionMapper, Acquisition> implements AcquisitionService {

    @Autowired
    private AcquisitionMapper acquisitionMapper;

    //分页查询
    @Override
    public PageInfo<Acquisition> findPageInfo(Integer pageIndex, Integer pageSize,Map mp) {
        PageInfo<Acquisition> pi = new PageInfo<Acquisition>();
        pi.setPageIndex(pageIndex);
        pi.setPageSize(pageSize);
        mp.put("currentPage",(pi.getPageIndex()-1)*pi.getPageSize());
        mp.put("pageSize",pi.getPageSize());
        System.out.println("pageIndex："+pageIndex);
        System.out.println("pageSize："+pageSize);
        System.out.println("pi.getPageIndex()-1)*pi.getPageSize()："+(pi.getPageIndex()-1)*pi.getPageSize());
        //获取总条数
        Integer totalCount = acquisitionMapper.totalCount(mp);
        if (totalCount>0){
            pi.setTotalCount(totalCount);
            //按条件查询
            List<Acquisition> acquisitionList = acquisitionMapper.getAcquisitionList(mp);
            pi.setList(acquisitionList);
        }
        return pi;
    }


    //添加
    @Override
    public int addAcquisition(Acquisition acquisition) {
        acquisition.setId(RandomUtil.getRandomIdByUUID());
        return acquisitionMapper.addAcquisition(acquisition);
    }


    //根据id删除
    @Override
    public int deleteAcquisition(String id) {
        return acquisitionMapper.deleteAcquisition(id);
    }


    //修改信息
    @Override
    public int updateAcquisition(Acquisition acquisition) {
        return acquisitionMapper.updateAcquisition(acquisition);
    }

    //根据ID查询
    @Override
    public Acquisition findAcquisitionById (String id){
        Acquisition d = acquisitionMapper.findAcquisitionById(id);
        return  d;
    }

    //查询所有
    @Override
    public List<Acquisition> getAll(){
        List<Acquisition> acquisitionList = acquisitionMapper.getAll();
        return acquisitionList;
    }

    //按照条件查询
    @Override
    public List<Acquisition> queryFilter(Map mp){
        List<Acquisition> acquisitionList = acquisitionMapper.queryFilter(mp);
        return acquisitionList;
    }
}
