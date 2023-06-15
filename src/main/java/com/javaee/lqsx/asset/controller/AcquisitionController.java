package com.javaee.lqsx.asset.controller;

import com.javaee.lqsx.asset.po.Acquisition;
import com.javaee.lqsx.asset.po.Assets;
import com.javaee.lqsx.asset.po.Manage;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.AcquisitionService;
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

//资产购置
@Controller
@RequestMapping(value = "/acquisition")
public class AcquisitionController {

    // 依赖注入
    @Autowired
    private AcquisitionService acquisitionService;
    @Autowired
    private AssetsService assetsService;
    @Autowired
    private ManageService manageService;


    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @RequestMapping(value = "/findAcquisition")
    public String findAcquisition(Integer pageIndex, Integer pageSize,String no,String name, Model model,HttpServletRequest request) {
        System.out.println("pageIndex："+pageIndex);
        System.out.println("pageSize："+pageSize);
        System.out.println("no："+no);

        HttpSession session = request.getSession();
        if(session.getAttribute("ad") == null){
            session.setAttribute("msg", "对不起，请登录！");
            return "login";
        }
        Map mp = new HashMap();
        String type = (String)session.getAttribute("type");
        if(type.equals("02")){
            Manage manage = (Manage)session.getAttribute("ad");
            mp.put("apply",manage.getId());
        }
        mp.put("no",no);
        mp.put("name",name);
        PageInfo<Acquisition> pageList = acquisitionService.findPageInfo(pageIndex,pageSize,mp);
//        System.out.println(pageList.getList());
        model.addAttribute("pageList",pageList);
        List<Assets> assetsList = assetsService.getAll();
//        System.out.println(assetsList);
        model.addAttribute("assetsList",assetsList);
        return "AcquisitionList";
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
        Acquisition acquisition = new Acquisition();
        acquisition.setId(id);
        acquisition.setStatus(status);
        int d = acquisitionService.updateAcquisition(acquisition);
        return "200";
    }


    /**
     * 导出excel
     */
    @RequestMapping("/downExcel")
    public void downExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        String type = (String)session.getAttribute("type");
        List<Acquisition> acquisitions = new ArrayList<>();
        if(type.equals("02")){
            Manage manage = (Manage)session.getAttribute("ad");
            Map mp = new HashMap();
            mp.put("apply",manage.getId());
            acquisitions = acquisitionService.queryFilter(mp);
            for(int i=0;i<acquisitions.size();i++){
                if(acquisitions.get(i).getMid()!=null){
                    Manage manageById = manageService.findManageById(acquisitions.get(i).getApply());
                    acquisitions.get(i).setMname(manageById.getName());
                }
                if(acquisitions.get(i).getStatus().equals("01")){
                    acquisitions.get(i).setStatus("已申请");
                }
                if(acquisitions.get(i).getStatus().equals("02")){
                    acquisitions.get(i).setStatus("已同意");
                }
                if(acquisitions.get(i).getStatus().equals("03")){
                    acquisitions.get(i).setStatus("已拒绝");
                }
            }
        }
        String[] columnNames = { "ID","资产编号","资产型号","资产名称","单价", "购置人","生产厂商", "生产日期", "入库时间", "资产类型",  "状态",  "申请人ID",  "申请人名称",  "创建时间"};
        String fileName = "资产购置信息表";
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        exportExcelUtil.mtyExcel(fileName, fileName, columnNames, acquisitions, response, ExportExcelUtil.EXCEL_FILE_2003);
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/addAcquisition" ,method = RequestMethod.POST)
    @ResponseBody
    public String addAcquisition( @RequestBody Acquisition acquisition,HttpServletRequest request) {
        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("ad") == null){
                session.setAttribute("msg", "对不起，请登录！");
                return "login";
            }
            Manage manage = (Manage)session.getAttribute("ad");
            acquisition.setApply(manage.getId());
            acquisition.setStatus("01");
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            acquisition.setCreateTime(sf.format(new Date()));
            acquisitionService.addAcquisition(acquisition);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 删除
     */
    @RequestMapping( "/deleteAcquisition")
    @ResponseBody
    public String deleteAcquisition(String id) {
        int d = acquisitionService.deleteAcquisition(id);
        return "AcquisitionList";
    }


    /**
     * 修改
     */
    @RequestMapping( "/updateAcquisition")
    @ResponseBody
    public String updateAcquisition(@RequestBody  Acquisition acquisition) {
        try{
            acquisition.setStatus("01");
            acquisitionService.updateAcquisition(acquisition);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 按照ID查询
     */
    @RequestMapping( "/findAcquisitionById")
    @ResponseBody
    public Acquisition findAcquisitionById(String id,Model model,HttpServletRequest request) {
        Acquisition acquisition= acquisitionService.findAcquisitionById(id);
        return acquisition;
    }


}
