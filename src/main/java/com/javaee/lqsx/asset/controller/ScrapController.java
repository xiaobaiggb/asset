package com.javaee.lqsx.asset.controller;

import com.javaee.lqsx.asset.po.*;
import com.javaee.lqsx.asset.service.AssetsService;
import com.javaee.lqsx.asset.util.ExportExcelUtil;
import com.javaee.lqsx.asset.po.*;
import com.javaee.lqsx.asset.service.ScrapService;
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

//资产报废信息
@Controller
@RequestMapping(value = "/scrap")
public class ScrapController {

    // 依赖注入
    @Autowired
    private ScrapService scrapService;
    @Autowired
    private AssetsService assetsService;

    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @RequestMapping(value = "/findScrap")
    public String findScrap(Integer pageIndex, Integer pageSize, String aid,Model model,HttpServletRequest request) {
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
            List<Assets> assetsList = assetsService.queryFilter(mp);
            model.addAttribute("assetsList",assetsList);
        }
        if(type.equals("03")){
            User user = (User)session.getAttribute("ad");
            mp.put("uid",user.getId());
            List<Assets> assetsList = assetsService.queryFilter(mp);
            model.addAttribute("assetsList",assetsList);
        }
        mp.put("aid",aid);
        PageInfo<Scrap> pageList = scrapService.findPageInfo(pageIndex,pageSize,mp);
        model.addAttribute("pageList",pageList);

//        List<Assets> assetsList = assetsService.queryFilter(mp);
//        model.addAttribute("assetsList",assetsList);

        return "ScrapList";
    }



    /**
     * 导出excel
     */
    @RequestMapping("/downExcel")
    public void downExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        String type = (String)session.getAttribute("type");
        List<Scrap> scrapList = new ArrayList<>();
        if(type.equals("02")){
            Manage manage = (Manage)session.getAttribute("ad");
            Map mp = new HashMap();
            mp.put("mid",manage.getId());
            scrapList = scrapService.queryFilter(mp);
            for(int i=0;i<scrapList.size();i++){
                if(scrapList.get(i).getAid()!=null){
                    Assets assetsById = assetsService.findAssetsById(scrapList.get(i).getAid());
                    scrapList.get(i).setAname(assetsById.getName());
                }
                if(!scrapList.get(i).getInfo().equals("自然损耗")){
                    scrapList.get(i).setInfo(scrapList.get(i).getUname());
                }
                if(scrapList.get(i).getStatus()!=null){
                    if(scrapList.get(i).getStatus().equals("01")){
                        scrapList.get(i).setStatus("已申请");
                    }
                    if(scrapList.get(i).getStatus().equals("02")){
                        scrapList.get(i).setStatus("已同意");
                    }
                    if(scrapList.get(i).getStatus().equals("03")){
                        scrapList.get(i).setStatus("已拒绝");
                    }
                }
            }
        }


        String[] columnNames = { "ID","资产ID","资产名称","报废负责人ID","报废负责人","经手人ID", "经手人名称", "备注","报废时间",  "状态",  "创建时间"};
        String fileName = "资产报废信息";
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        exportExcelUtil.mtyExcel(fileName, fileName, columnNames, scrapList, response, ExportExcelUtil.EXCEL_FILE_2003);
    }


    /**
     * 审批
     */
    @RequestMapping( "/shenpi")
    @ResponseBody
    public String shenpi(String id,String status,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("ad") == null){
            session.setAttribute("msg", "对不起，请登录！");
            return "login";
        }
        Scrap scrap = new Scrap();
        scrap.setId(id);
        scrap.setStatus(status);
        int d = scrapService.updateScrap(scrap);
        return "200";
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/addScrap" ,method = RequestMethod.POST)
    @ResponseBody
    public String addScrap( @RequestBody Scrap scrap,HttpServletRequest request) {
        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("ad") == null){
                session.setAttribute("msg", "对不起，请登录！");
                return "login";
            }
            String type = (String)session.getAttribute("type");
            if(type.equals("02")){
                Manage manage = (Manage)session.getAttribute("ad");
                scrap.setMid(manage.getId());
                scrap.setStatus("02");
            }
            if(type.equals("03")){
                User user = (User)session.getAttribute("ad");
                scrap.setInfo(user.getId());
                Assets assetsById = assetsService.findAssetsById(scrap.getAid());
                scrap.setMid(assetsById.getMid());
                scrap.setStatus("01");
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            scrap.setCreateTime(sf.format(new Date()));
            scrapService.addScrap(scrap);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 删除
     */
    @RequestMapping( "/deleteScrap")
    @ResponseBody
    public String deleteScrap(String id) {
        int d = scrapService.deleteScrap(id);
        return "ScrapList";
    }


    /**
     * 修改
     */
    @RequestMapping( "/updateScrap")
    @ResponseBody
    public String updateScrap(@RequestBody  Scrap scrap) {
        try{
            scrapService.updateScrap(scrap);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 按照ID查询
     */
    @RequestMapping( "/findScrapById")
    @ResponseBody
    public Scrap findScrapById(String id,Model model,HttpServletRequest request) {
        Scrap scrap= scrapService.findScrapById(id);
        return scrap;
    }


}
