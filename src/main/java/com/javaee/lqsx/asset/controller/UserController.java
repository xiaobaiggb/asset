package com.javaee.lqsx.asset.controller;

import com.javaee.lqsx.asset.po.User;
import com.javaee.lqsx.asset.po.PageInfo;
import com.javaee.lqsx.asset.service.UserService;
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

//员工信息
@Controller
@RequestMapping(value = "/user")
public class UserController {

    // 依赖注入
    @Autowired
    private UserService userService;


    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @RequestMapping(value = "/findUser")
    public String findUser(Integer pageIndex, Integer pageSize,String no,String name, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("ad") == null){
            session.setAttribute("msg", "对不起，请登录！");
            return "login";
        }
        Map mp = new HashMap();
        mp.put("no",no);
        mp.put("name",name);
        PageInfo<User> pageList = userService.findPageInfo(pageIndex,pageSize,mp);
        model.addAttribute("pageList",pageList);
        return "UserList";
    }


    /**
     * 添加
     */
    @RequestMapping(value = "/addUser" ,method = RequestMethod.POST)
    @ResponseBody
    public String addUser( @RequestBody User user) {
        try{
            List<User> all = userService.getAll();
            for(int i=0;i<all.size();i++){
                if(all.get(i).getNo().equals(user.getNo())){
                    return "202";
                }
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            user.setCreateTime(sf.format(new Date()));
            userService.addUser(user);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 删除
     */
    @RequestMapping( "/deleteUser")
    @ResponseBody
    public String deleteUser(String id) {
        int d = userService.deleteUser(id);
        return "UserList";
    }


    /**
     * 修改
     */
    @RequestMapping( "/updateUser")
    @ResponseBody
    public String updateUser(@RequestBody  User user) {
        try{
            List<User> all = userService.getAll();
            for(int i=0;i<all.size();i++){
                if(all.get(i).getNo().equals(user.getNo()) && !all.get(i).getId().equals(user.getId())){
                    return "202";
                }
            }
            userService.updateUser(user);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 按照ID查询
     */
    @RequestMapping( "/findUserById")
    @ResponseBody
    public User findUserById(String id,Model model,HttpServletRequest request) {
        User user= userService.findUserById(id);
        return user;
    }


}
