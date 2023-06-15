package com.javaee.lqsx.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaee.lqsx.asset.mapper.RecipientsMapper;
import com.javaee.lqsx.asset.po.Recipients;
import com.javaee.lqsx.asset.util.RandomUtil;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.RecipientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import com.javaee.lqsx.asset.util.*;

/**
 * 用户Service接口实现类
 */

@Service("recipientsService")
@Transactional
public class RecipientsServiceImpl  extends ServiceImpl<RecipientsMapper, Recipients> implements RecipientsService {

    @Autowired
    private RecipientsMapper recipientsMapper;


    //分页查询
    @Override
    public PageInfo<Recipients> findPageInfo(Integer pageIndex, Integer pageSize,Map mp) {
        PageInfo<Recipients> pi = new PageInfo<Recipients>();
        pi.setPageIndex(pageIndex);
        pi.setPageSize(pageSize);
        mp.put("currentPage",(pi.getPageIndex()-1)*pi.getPageSize());
        mp.put("pageSize",pi.getPageSize());
        //获取总条数
        Integer totalCount = recipientsMapper.totalCount(mp);
        if (totalCount>0){
            pi.setTotalCount(totalCount);
            //按条件查询
            List<Recipients> recipientsList =	recipientsMapper.getRecipientsList(mp);
            pi.setList(recipientsList);
        }
        return pi;
    }


    //添加
    @Override
    public int addRecipients(Recipients recipients) {
        recipients.setId(RandomUtil.getRandomIdByUUID());
        return recipientsMapper.addRecipients(recipients);
    }


    //根据id删除
    @Override
    public int deleteRecipients(String id) {
        return recipientsMapper.deleteRecipients(id);
    }


    //修改信息
    @Override
    public int updateRecipients(Recipients recipients) {
        return recipientsMapper.updateRecipients(recipients);
    }

    //根据ID查询
    @Override
    public Recipients findRecipientsById (String id){
        Recipients d = recipientsMapper.findRecipientsById(id);
        return  d;
    }

    //查询所有
    @Override
    public List<Recipients> getAll(){
        List<Recipients> recipientsList = recipientsMapper.getAll();
        return recipientsList;
    }

    //按照条件查询
    @Override
    public List<Recipients> queryFilter(Map mp){
        List<Recipients> recipientsList = recipientsMapper.queryFilter(mp);
        return recipientsList;
    }
}
