package com.javaee.lqsx.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaee.lqsx.asset.po.Recipients;
import org.apache.ibatis.annotations.Param;
import java.util.*;

/**
 * DAO层接口
 */

//资产领用信息功能接口
public interface RecipientsMapper extends BaseMapper<Recipients>{

    //获取列表
    public List<Recipients> getRecipientsList(Map mp);

    //获取总条数
    public Integer totalCount(Map mp);

    //添加
    public int addRecipients(Recipients recipients);

    //删除
    public int deleteRecipients(@Param("id") String id);

    //修改
    public int updateRecipients(Recipients recipients);

    //根据ID查询
    public Recipients findRecipientsById(@Param("id") String id);

    //查询所有
    public List<Recipients> getAll();

    //按照条件查询
    public List<Recipients> queryFilter(Map mp);

}
