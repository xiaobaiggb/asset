package com.javaee.lqsx.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaee.lqsx.asset.po.Assets;
import org.apache.ibatis.annotations.Param;
import java.util.*;

/**
 * DAO层接口
 */

//资产信息功能接口
public interface AssetsMapper extends BaseMapper<Assets>{

    //获取列表
    public List<Assets> getAssetsList(Map mp);

    //获取总条数
    public Integer totalCount(Map mp);

    //添加
    public int addAssets(Assets assets);

    //删除
    public int deleteAssets(@Param("id") String id);

    //修改
    public int updateAssets(Assets assets);

    //根据ID查询
    public Assets findAssetsById(@Param("id") String id);

    //查询所有
    public List<Assets> getAll();

    //按照条件查询
    public List<Assets> queryFilter(Map mp);

}
