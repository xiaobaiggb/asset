package com.javaee.lqsx.asset.controller;

import com.javaee.lqsx.asset.po.*;
import com.javaee.lqsx.asset.service.AssetsService;
import com.javaee.lqsx.asset.util.ExportExcelUtil;
import com.javaee.lqsx.asset.po.*;
import com.javaee.lqsx.asset.service.ReturnsService;
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

//资产归还信息
@Controller
@RequestMapping(value = "/returns")
public class ReturnsController {

    // 依赖注入
    @Autowired
    private ReturnsService returnsService;
    @Autowired
    private AssetsService assetsService;

    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @RequestMapping(value = "/findReturns")
    public String findReturns(Integer pageIndex, Integer pageSize, String aid, Model model,HttpServletRequest request) {
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
        if(type.equals("03")){
            User user = (User)session.getAttribute("ad");
            mp.put("uid",user.getId());
        }
        mp.put("aid",aid);
        PageInfo<Returns> pageList = returnsService.findPageInfo(pageIndex,pageSize,mp);
        model.addAttribute("pageList",pageList);
        return "ReturnsList";
    }


    /**
     * 添加
     */
    @RequestMapping(value = "/addReturns" ,method = RequestMethod.POST)
    @ResponseBody
    public String addReturns( @RequestBody Returns returns) {
        try{
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            returns.setCreateTime(sf.format(new Date()));
            returnsService.addReturns(returns);
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
        List<Returns> returnsList = new ArrayList<>();
        if(type.equals("02")){
            Manage manage = (Manage)session.getAttribute("ad");
            Map mp = new HashMap();
            mp.put("mid",manage.getId());
            returnsList = returnsService.queryFilter(mp);
            for(int i=0;i<returnsList.size();i++){
                if(returnsList.get(i).getAid()!=null){
                    Assets assetsById = assetsService.findAssetsById(returnsList.get(i).getAid());
                    returnsList.get(i).setAname(assetsById.getName());
                }
                if(returnsList.get(i).getStatus().equals("01")){
                    returnsList.get(i).setStatus("已归还");
                }
            }
        }
        String[] columnNames = { "ID","资产ID","资产名称", "员工ID","员工名称", "归还时间",  "状态",  "创建时间"};
        String fileName = "资产归还表";
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        exportExcelUtil.mtyExcel(fileName, fileName, columnNames, returnsList, response, ExportExcelUtil.EXCEL_FILE_2003);
    }



    /**
     * 删除
     */
    @RequestMapping( "/deleteReturns")
    @ResponseBody
    public String deleteReturns(String id) {
        int d = returnsService.deleteReturns(id);
        return "ReturnsList";
    }


    /**
     * 修改
     */
    @RequestMapping( "/updateReturns")
    @ResponseBody
    public String updateReturns(@RequestBody  Returns returns) {
        try{
            returnsService.updateReturns(returns);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 按照ID查询
     */
    @RequestMapping( "/findReturnsById")
    @ResponseBody
    public Returns findReturnsById(String id,Model model,HttpServletRequest request) {
        Returns returns= returnsService.findReturnsById(id);
        return returns;
    }


}
