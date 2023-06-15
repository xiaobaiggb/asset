package com.javaee.lqsx.asset.controller;

import com.javaee.lqsx.asset.po.*;
import com.javaee.lqsx.asset.service.AssetsService;
import com.javaee.lqsx.asset.util.ExportExcelUtil;
import com.javaee.lqsx.asset.po.*;
import com.javaee.lqsx.asset.service.RepairService;
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

//资产维修信息
@Controller
@RequestMapping(value = "/repair")
public class RepairController {

    // 依赖注入
    @Autowired
    private RepairService repairService;
    @Autowired
    private AssetsService assetsService;

    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @RequestMapping(value = "/findRepair")
    public String findRepair(Integer pageIndex, Integer pageSize,String aid,String repairTime, Model model,HttpServletRequest request) {
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
        mp.put("repairTime",repairTime);
        PageInfo<Repair> pageList = repairService.findPageInfo(pageIndex,pageSize,mp);
        model.addAttribute("pageList",pageList);

//        List<Assets> assetsList = assetsService.queryFilter(mp);
//        model.addAttribute("assetsList",assetsList);
        return "RepairList";
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
        Repair repair = new Repair();
        repair.setId(id);
        repair.setStatus(status);
        int d = repairService.updateRepair(repair);
        return "200";
    }



    /**
     * 导出excel
     */
    @RequestMapping("/downExcel")
    public void downExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        String type = (String)session.getAttribute("type");
        List<Repair> repairList = new ArrayList<>();
        if(type.equals("02")){
            Manage manage = (Manage)session.getAttribute("ad");
            Map mp = new HashMap();
            mp.put("mid",manage.getId());
            repairList = repairService.queryFilter(mp);
            for(int i=0;i<repairList.size();i++){
                if(repairList.get(i).getAid()!=null){
                    Assets assetsById = assetsService.findAssetsById(repairList.get(i).getAid());
                    repairList.get(i).setAname(assetsById.getName());
                }
                if(!repairList.get(i).getInfo().equals("自然损耗")){
                    repairList.get(i).setInfo(repairList.get(i).getUname());
                }
                if(repairList.get(i).getStatus()!=null){
                    if(repairList.get(i).getStatus().equals("01")){
                        repairList.get(i).setStatus("已申请");
                    }
                    if(repairList.get(i).getStatus().equals("02")){
                        repairList.get(i).setStatus("已同意");
                    }
                    if(repairList.get(i).getStatus().equals("03")){
                        repairList.get(i).setStatus("已拒绝");
                    }
                }
            }
        }


        String[] columnNames = { "ID","资产ID","资产名称","送修负责人ID","送修负责人","经手人ID", "经手人名称", "备注","维修时间",  "状态",  "创建时间"};
        String fileName = "资产维修信息";
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        exportExcelUtil.mtyExcel(fileName, fileName, columnNames, repairList, response, ExportExcelUtil.EXCEL_FILE_2003);
    }


    /**
     * 添加
     */
    @RequestMapping(value = "/addRepair" ,method = RequestMethod.POST)
    @ResponseBody
    public String addRepair( @RequestBody Repair repair,HttpServletRequest request) {
        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("ad") == null){
                session.setAttribute("msg", "对不起，请登录！");
                return "login";
            }
            String type = (String)session.getAttribute("type");
            if(type.equals("02")){
                Manage manage = (Manage)session.getAttribute("ad");
                repair.setMid(manage.getId());
                repair.setStatus("02");
            }
            if(type.equals("03")){
                User user = (User)session.getAttribute("ad");
                repair.setInfo(user.getId());
                Assets assetsById = assetsService.findAssetsById(repair.getAid());
                repair.setMid(assetsById.getMid());
                repair.setStatus("01");
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            repair.setCreateTime(sf.format(new Date()));
            repairService.addRepair(repair);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 删除
     */
    @RequestMapping( "/deleteRepair")
    @ResponseBody
    public String deleteRepair(String id) {
        int d = repairService.deleteRepair(id);
        return "RepairList";
    }


    /**
     * 修改
     */
    @RequestMapping( "/updateRepair")
    @ResponseBody
    public String updateRepair(@RequestBody  Repair repair) {
        try{
            repairService.updateRepair(repair);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 按照ID查询
     */
    @RequestMapping( "/findRepairById")
    @ResponseBody
    public Repair findRepairById(String id,Model model,HttpServletRequest request) {
        Repair repair= repairService.findRepairById(id);
        return repair;
    }


}
