package com.javaee.lqsx.asset.controller;

import com.javaee.lqsx.asset.po.Manage;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @description: 控制层
 */

//资产管理员
@Controller
@RequestMapping(value = "/manage")
public class ManageController {

    // 依赖注入
    @Autowired
    private ManageService manageService;


    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @RequestMapping(value = "/findManage")
    public String findManage(Integer pageIndex, Integer pageSize, String no,String name,Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("ad") == null){
            session.setAttribute("msg", "对不起，请登录！");
            return "login";
        }
        Map mp = new HashMap();
        mp.put("type","01");
        mp.put("no",no);
        mp.put("name",name);
        PageInfo<Manage> pageList = manageService.findPageInfo(pageIndex,pageSize,mp);
        model.addAttribute("pageList",pageList);
        return "ManageList";
    }


    @RequestMapping(value = "/findManage2")
    public String findManage2(Integer pageIndex, Integer pageSize, String no,String name,Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("ad") == null){
            session.setAttribute("msg", "对不起，请登录！");
            return "login";
        }
        Map mp = new HashMap();
        mp.put("type","02");
        mp.put("no",no);
        mp.put("name",name);
        PageInfo<Manage> pageList = manageService.findPageInfo(pageIndex,pageSize,mp);
        model.addAttribute("pageList",pageList);
        return "ManageList2";
    }



    /**
     * 添加
     */
    @RequestMapping(value = "/addManage" ,method = RequestMethod.POST)
    @ResponseBody
    public String addManage( @RequestBody Manage manage) {
        try{
            List<Manage> all = manageService.getAll();
            for(int i=0;i<all.size();i++){
                if(all.get(i).getNo().equals(manage.getNo())){
                    return "202";
                }
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            manage.setCreateTime(sf.format(new Date()));
            manageService.addManage(manage);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 删除
     */
    @RequestMapping( "/deleteManage")
    @ResponseBody
    public String deleteManage(String id) {
        int d = manageService.deleteManage(id);
        return "ManageList";
    }


    /**
     * 修改
     */
    @RequestMapping( "/updateManage")
    @ResponseBody
    public String updateManage(@RequestBody  Manage manage) {
        try{
            List<Manage> all = manageService.getAll();
            for(int i=0;i<all.size();i++){
                if(all.get(i).getNo().equals(manage.getNo()) && !all.get(i).getId().equals(manage.getId())){
                    return "202";
                }
            }
            manageService.updateManage(manage);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 按照ID查询
     */
    @RequestMapping( "/findManageById")
    @ResponseBody
    public Manage findManageById(String id,Model model,HttpServletRequest request) {
        Manage manage= manageService.findManageById(id);
        return manage;
    }


}
