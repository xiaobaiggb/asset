package com.javaee.lqsx.asset.controller;

import com.javaee.lqsx.asset.po.Assets;
import com.javaee.lqsx.asset.po.Manage;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.AssetsService;
import com.javaee.lqsx.asset.service.ManageService;
import com.javaee.lqsx.asset.util.ExportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @description: 控制层
 */

//资产信息
@Controller
@RequestMapping(value = "/assets")
public class AssetsController {

    // 依赖注入
    @Autowired
    private AssetsService assetsService;
    @Autowired
    private ManageService manageService;

    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @RequestMapping(value = "/findAssets")
    public String findAssets(Integer pageIndex, Integer pageSize,String no,String name, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("ad") == null){
            session.setAttribute("msg", "对不起，请登录！");
            return "login";
        }
        Map mp = new HashMap();
        String type = (String)session.getAttribute("type");
        if(type.equals("02")){
            Manage manage = (Manage)session.getAttribute("ad");
            mp.put("mid",manage.getId());
        }
        mp.put("no",no);
        mp.put("name",name);
        PageInfo<Assets> pageList = assetsService.findPageInfo(pageIndex,pageSize,mp);
        model.addAttribute("pageList",pageList);
        return "AssetsList";
    }

    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @RequestMapping(value = "/findAssets2")
    public String findAssets2(Integer pageIndex, Integer pageSize,String no,String name, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("ad") == null){
            session.setAttribute("msg", "对不起，请登录！");
            return "login";
        }
        Map mp = new HashMap();
        String type = (String)session.getAttribute("type");
        if(type.equals("02")){
            Manage manage = (Manage)session.getAttribute("ad");
            mp.put("mid",manage.getId());
        }
        mp.put("no",no);
        mp.put("name",name);
        PageInfo<Assets> pageList = assetsService.findPageInfo(pageIndex,pageSize,mp);
        model.addAttribute("pageList",pageList);
        return "AssetsList2";
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/addAssets" ,method = RequestMethod.POST)
    @ResponseBody
    public String addAssets( @RequestBody Assets assets,HttpServletRequest request) {
        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("ad") == null){
                session.setAttribute("msg", "对不起，请登录！");
                return "login";
            }
            List<Assets> all = assetsService.getAll();
            for(int i=0;i<all.size();i++){
                if(all.get(i).getNo().equals(assets.getNo())){
                    return "202";
                }
            }
            Manage manage = (Manage)session.getAttribute("ad");
            assets.setMid(manage.getId());
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            assets.setCreateTime(sf.format(new Date()));
            assetsService.addAssets(assets);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }

    /**
     * 导出excel
     */
    @RequestMapping("/downExcel")
    public void downExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        String type = (String)session.getAttribute("type");
        List<Assets> assetsList = new ArrayList<>();
        if(type.equals("02")){
            Manage manage = (Manage)session.getAttribute("ad");
            Map mp = new HashMap();
            mp.put("mid",manage.getId());
            assetsList = assetsService.queryFilter(mp);
            for(int i=0;i<assetsList.size();i++){
                if(assetsList.get(i).getMid()!=null){
                    Manage manageById = manageService.findManageById(assetsList.get(i).getMid());
                    assetsList.get(i).setMname(manageById.getName());
                }
                if(assetsList.get(i).getStatus().equals("01")){
                    assetsList.get(i).setStatus("已申请");
                }
                if(assetsList.get(i).getStatus().equals("02")){
                    assetsList.get(i).setStatus("已同意");
                }
                if(assetsList.get(i).getStatus().equals("03")){
                    assetsList.get(i).setStatus("已拒绝");
                }
            }
        }
        String[] columnNames = { "ID","资产编号","资产型号","资产名称","单价", "生产厂商", "生产日期", "入库时间", "购买人", "购买人",  "资产类型",  "状态",  "创建时间"};
        String fileName = "资产信息表";
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        exportExcelUtil.mtyExcel(fileName, fileName, columnNames, assetsList, response, ExportExcelUtil.EXCEL_FILE_2003);
    }


    /**
     * 删除
     */
    @RequestMapping( "/deleteAssets")
    @ResponseBody
    public String deleteAssets(String id) {
        int d = assetsService.deleteAssets(id);
        return "AssetsList";
    }


    /**
     * 修改
     */
    @RequestMapping( "/updateAssets")
    @ResponseBody
    public String updateAssets(@RequestBody  Assets assets) {
        try{
            List<Assets> all = assetsService.getAll();
            for(int i=0;i<all.size();i++){
                if(all.get(i).getNo().equals(assets.getNo()) && !all.get(i).getId().equals(assets.getId())){
                    return "202";
                }
            }
            assetsService.updateAssets(assets);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 按照ID查询
     */
    @RequestMapping( "/findAssetsById")
    @ResponseBody
    public Assets findAssetsById(String id,Model model,HttpServletRequest request) {
        Assets assets= assetsService.findAssetsById(id);
        return assets;
    }


}
