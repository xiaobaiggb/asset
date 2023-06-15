package com.javaee.lqsx.asset.controller;

import com.javaee.lqsx.asset.po.*;
import com.javaee.lqsx.asset.service.AssetsService;
import com.javaee.lqsx.asset.service.DeprecitionService;
import com.javaee.lqsx.asset.util.ExportExcelUtil;
import com.javaee.lqsx.asset.po.*;
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

//资产折旧信息
@Controller
@RequestMapping(value = "/deprecition")
public class DeprecitionController {

    // 依赖注入
    @Autowired
    private DeprecitionService deprecitionService;
    @Autowired
    private AssetsService assetsService;

    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @RequestMapping(value = "/findDeprecition")
    public String findDeprecition(Integer pageIndex, Integer pageSize,String aid, Model model,HttpServletRequest request) {
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
//            List<Assets> assetsList = assetsService.queryFilter(mp);
//            model.addAttribute("assetsList",assetsList);
        }
        if(type.equals("03")){
            User user = (User)session.getAttribute("ad");
            mp.put("uid",user.getId());
//            List<Assets> assetsList = assetsService.queryFilter(mp);
//            model.addAttribute("assetsList",assetsList);
        }
        mp.put("aid",aid);
        PageInfo<Deprecition> pageList = deprecitionService.findPageInfo(pageIndex,pageSize,mp);
        model.addAttribute("pageList",pageList);

        List<Assets> assetsList = assetsService.queryFilter(mp);
        model.addAttribute("assetsList",assetsList);

        return "DeprecitionList";
    }



    /**
     * 导出excel
     */
    @RequestMapping("/downExcel")
    public void downExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        String type = (String)session.getAttribute("type");
        List<Deprecition> deprecitionList = new ArrayList<>();
        if(type.equals("02")){
            Manage manage = (Manage)session.getAttribute("ad");
            Map mp = new HashMap();
            mp.put("mid",manage.getId());
            deprecitionList = deprecitionService.queryFilter(mp);
            for(int i=0;i<deprecitionList.size();i++){
                if(deprecitionList.get(i).getAid()!=null){
                    Assets assetsById = assetsService.findAssetsById(deprecitionList.get(i).getAid());
                    deprecitionList.get(i).setAname(assetsById.getName());
                }
                if(!deprecitionList.get(i).getInfo().equals("自然损耗")){
                    deprecitionList.get(i).setInfo(deprecitionList.get(i).getUname());
                }
                if(deprecitionList.get(i).getStatus()!=null){
                    if(deprecitionList.get(i).getStatus().equals("01")){
                        deprecitionList.get(i).setStatus("已申请");
                    }
                    if(deprecitionList.get(i).getStatus().equals("02")){
                        deprecitionList.get(i).setStatus("已同意");
                    }
                    if(deprecitionList.get(i).getStatus().equals("03")){
                        deprecitionList.get(i).setStatus("已拒绝");
                    }
                }
            }
        }

        String[] columnNames = { "ID","资产ID","资产名称","折旧负责人ID","折旧负责人","经手人ID", "经手人名称", "备注","折旧时间",  "状态",  "创建时间"};
        String fileName = "资产折旧信息";
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        exportExcelUtil.mtyExcel(fileName, fileName, columnNames, deprecitionList, response, ExportExcelUtil.EXCEL_FILE_2003);
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
        Deprecition deprecition = new Deprecition();
        deprecition.setId(id);
        deprecition.setStatus(status);
        int d = deprecitionService.updateDeprecition(deprecition);
        return "200";
    }


    /**
     * 添加
     */
    @RequestMapping(value = "/addDeprecition" ,method = RequestMethod.POST)
    @ResponseBody
    public String addDeprecition( @RequestBody Deprecition deprecition,HttpServletRequest request) {
        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("ad") == null){
                session.setAttribute("msg", "对不起，请登录！");
                return "login";
            }
            String type = (String)session.getAttribute("type");
            if(type.equals("02")){
                Manage manage = (Manage)session.getAttribute("ad");
                deprecition.setMid(manage.getId());
                deprecition.setStatus("02");
            }
            if(type.equals("03")){
                User user = (User)session.getAttribute("ad");
                deprecition.setInfo(user.getId());
                Assets assetsById = assetsService.findAssetsById(deprecition.getAid());
                deprecition.setMid(assetsById.getMid());
                deprecition.setStatus("01");
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            deprecition.setCreateTime(sf.format(new Date()));
            deprecitionService.addDeprecition(deprecition);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 删除
     */
    @RequestMapping( "/deleteDeprecition")
    @ResponseBody
    public String deleteDeprecition(String id) {
        int d = deprecitionService.deleteDeprecition(id);
        return "DeprecitionList";
    }


    /**
     * 修改
     */
    @RequestMapping( "/updateDeprecition")
    @ResponseBody
    public String updateDeprecition(@RequestBody  Deprecition deprecition) {
        try{
            deprecitionService.updateDeprecition(deprecition);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 按照ID查询
     */
    @RequestMapping( "/findDeprecitionById")
    @ResponseBody
    public Deprecition findDeprecitionById(String id,Model model,HttpServletRequest request) {
        Deprecition deprecition= deprecitionService.findDeprecitionById(id);
        return deprecition;
    }


}
