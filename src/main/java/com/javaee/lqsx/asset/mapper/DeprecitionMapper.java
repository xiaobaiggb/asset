package com.javaee.lqsx.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaee.lqsx.asset.po.Deprecition;
import org.apache.ibatis.annotations.Param;
import java.util.*;

/**
 * DAO层接口
 */

//资产折旧信息功能接口
public interface DeprecitionMapper extends BaseMapper<Deprecition>{

    //获取列表
    public List<Deprecition> getDeprecitionList(Map mp);

    //获取总条数
    public Integer totalCount(Map mp);

    //添加
    public int addDeprecition(Deprecition deprecition);

    //删除
    public int deleteDeprecition(@Param("id") String id);

    //修改
    public int updateDeprecition(Deprecition deprecition);

    //根据ID查询
    public Deprecition findDeprecitionById(@Param("id") String id);

    //查询所有
    public List<Deprecition> getAll();

    //按照条件查询
    public List<Deprecition> queryFilter(Map mp);

}
