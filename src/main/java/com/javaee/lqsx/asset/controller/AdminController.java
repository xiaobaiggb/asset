package com.javaee.lqsx.asset.controller;

import com.javaee.lqsx.asset.po.Admin;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.AdminService;
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

//管理员
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    // 依赖注入
    @Autowired
    private AdminService adminService;


    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @RequestMapping(value = "/findAdmin")
    public String findAdmin(Integer pageIndex, Integer pageSize,String no, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("ad") == null){
            session.setAttribute("msg", "对不起，请登录！");
            return "login";
        }
        Map mp = new HashMap();
        mp.put("no",no);
        PageInfo<Admin> pageList = adminService.findPageInfo(pageIndex,pageSize,mp);
        model.addAttribute("pageList",pageList);
        return "AdminList";
    }


    /**
     * 添加
     */
    @RequestMapping(value = "/addAdmin" ,method = RequestMethod.POST)
    @ResponseBody
    public String addAdmin( @RequestBody Admin admin) {
        try{
            List<Admin> all = adminService.getAll();
            for(int i=0;i<all.size();i++){
                if(all.get(i).getNo().equals(admin.getNo())){
                    return "202";
                }
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            admin.setCreateTime(sf.format(new Date()));
            adminService.addAdmin(admin);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 删除
     */
    @RequestMapping( "/deleteAdmin")
    @ResponseBody
    public String deleteAdmin(String id) {
        int d = adminService.deleteAdmin(id);
        return "AdminList";
    }


    /**
     * 修改
     */
    @RequestMapping( "/updateAdmin")
    @ResponseBody
    public String updateAdmin(@RequestBody  Admin admin) {
        try{
            List<Admin> all = adminService.getAll();
            for(int i=0;i<all.size();i++){
                if(all.get(i).getNo().equals(admin.getNo()) && !all.get(i).getId().equals(admin.getId())){
                    return "202";
                }
            }
            adminService.updateAdmin(admin);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 按照ID查询
     */
    @RequestMapping( "/findAdminById")
    @ResponseBody
    public Admin findAdminById(String id,Model model,HttpServletRequest request) {
        Admin admin= adminService.findAdminById(id);
        return admin;
    }


}
