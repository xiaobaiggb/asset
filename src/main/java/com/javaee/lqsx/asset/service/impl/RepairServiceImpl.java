package com.javaee.lqsx.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaee.lqsx.asset.mapper.RepairMapper;
import com.javaee.lqsx.asset.util.RandomUtil;
import com.javaee.lqsx.asset.po.Repair;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import com.javaee.lqsx.asset.util.*;

/**
 * 用户Service接口实现类
 */

@Service("repairService")
@Transactional
public class RepairServiceImpl  extends ServiceImpl<RepairMapper, Repair> implements RepairService {

    @Autowired
    private RepairMapper repairMapper;


    //分页查询
    @Override
    public PageInfo<Repair> findPageInfo(Integer pageIndex, Integer pageSize,Map mp) {
        PageInfo<Repair> pi = new PageInfo<Repair>();
        pi.setPageIndex(pageIndex);
        pi.setPageSize(pageSize);
        mp.put("currentPage",(pi.getPageIndex()-1)*pi.getPageSize());
        mp.put("pageSize",pi.getPageSize());
        //获取总条数
        Integer totalCount = repairMapper.totalCount(mp);
        if (totalCount>0){
            pi.setTotalCount(totalCount);
            //按条件查询
            List<Repair> repairList =	repairMapper.getRepairList(mp);
            pi.setList(repairList);
        }
        return pi;
    }


    //添加
    @Override
    public int addRepair(Repair repair) {
        repair.setId(RandomUtil.getRandomIdByUUID());
        return repairMapper.addRepair(repair);
    }


    //根据id删除
    @Override
    public int deleteRepair(String id) {
        return repairMapper.deleteRepair(id);
    }


    //修改信息
    @Override
    public int updateRepair(Repair repair) {
        return repairMapper.updateRepair(repair);
    }

    //根据ID查询
    @Override
    public Repair findRepairById (String id){
        Repair d = repairMapper.findRepairById(id);
        return  d;
    }

    //查询所有
    @Override
    public List<Repair> getAll(){
        List<Repair> repairList = repairMapper.getAll();
        return repairList;
    }

    //按照条件查询
    @Override
    public List<Repair> queryFilter(Map mp){
        List<Repair> repairList = repairMapper.queryFilter(mp);
        return repairList;
    }
}
