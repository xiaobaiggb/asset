package com.javaee.lqsx.asset.controller;

import com.javaee.lqsx.asset.po.*;
import com.javaee.lqsx.asset.service.AssetsService;
import com.javaee.lqsx.asset.util.ExportExcelUtil;
import com.javaee.lqsx.asset.po.*;
import com.javaee.lqsx.asset.service.RecipientsService;
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

//资产领用信息
@Controller
@RequestMapping(value = "/recipients")
public class RecipientsController {

    // 依赖注入
    @Autowired
    private RecipientsService recipientsService;
    @Autowired
    private AssetsService assetsService;
    @Autowired
    private ReturnsService returnsService;

    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @RequestMapping(value = "/findRecipients")
    public String findRecipients(Integer pageIndex, Integer pageSize,String aid, Model model,HttpServletRequest request) {
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
        PageInfo<Recipients> pageList = recipientsService.findPageInfo(pageIndex,pageSize,mp);
        model.addAttribute("pageList",pageList);
        List<Assets> assetsList = assetsService.getAll();
        model.addAttribute("assetsList",assetsList);
        return "RecipientsList";
    }

    /**
     * 导出excel
     */
    @RequestMapping("/downExcel")
    public void downExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        String type = (String)session.getAttribute("type");
        List<Recipients> recipients = new ArrayList<>();
        if(type.equals("02")){
            Manage manage = (Manage)session.getAttribute("ad");
            Map mp = new HashMap();
            mp.put("mid",manage.getId());
            recipients = recipientsService.queryFilter(mp);
            for(int i=0;i<recipients.size();i++){
                if(recipients.get(i).getAid()!=null){
                    Assets assetsById = assetsService.findAssetsById(recipients.get(i).getAid());
                    recipients.get(i).setAname(assetsById.getName());
                }
                if(recipients.get(i).getStatus().equals("01")){
                    recipients.get(i).setStatus("已申请");
                }
                if(recipients.get(i).getStatus().equals("02")){
                    recipients.get(i).setStatus("已同意");
                }
                if(recipients.get(i).getStatus().equals("03")){
                    recipients.get(i).setStatus("已拒绝");
                }
                if(recipients.get(i).getStatus().equals("04")){
                    recipients.get(i).setStatus("已归还");
                }
            }
        }
        String[] columnNames = { "ID","资产ID","资产名称","员工名称", "员工ID", "申请时间",  "状态",  "创建时间"};
        String fileName = "资产申领表";
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        exportExcelUtil.mtyExcel(fileName, fileName, columnNames, recipients, response, ExportExcelUtil.EXCEL_FILE_2003);
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
        Recipients acquisition = new Recipients();
        acquisition.setId(id);
        acquisition.setStatus(status);
        int d = recipientsService.updateRecipients(acquisition);
        if(status.equals("04")){
            Date date = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sf.format(date);
            Recipients recipientsById = recipientsService.findRecipientsById(id);
            User user = (User)session.getAttribute("ad");
            Returns returns = new Returns();
            returns.setAid(recipientsById.getAid());
            returns.setUid(user.getId());
            returns.setReturnTime(time);
            returns.setCreateTime(time);
            returns.setStatus("01");
            returnsService.addReturns(returns);
        }
        return "200";
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/addRecipients" ,method = RequestMethod.POST)
    @ResponseBody
    public String addRecipients( @RequestBody Recipients recipients,HttpServletRequest request) {
        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("ad") == null){
                session.setAttribute("msg", "对不起，请登录！");
                return "login";
            }
            Map mps = new HashMap();
            mps.put("aid",recipients.getAid());
            mps.put("status1","01");
            List<Recipients> acquisition = recipientsService.queryFilter(mps);
            if(acquisition.size()>0){
                return "202";
            }
            User user = (User)session.getAttribute("ad");
            recipients.setUid(user.getId());
            recipients.setStatus("01");
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            recipients.setCreateTime(sf.format(new Date()));
            recipientsService.addRecipients(recipients);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 删除
     */
    @RequestMapping( "/deleteRecipients")
    @ResponseBody
    public String deleteRecipients(String id) {
        int d = recipientsService.deleteRecipients(id);
        return "RecipientsList";
    }


    /**
     * 修改
     */
    @RequestMapping( "/updateRecipients")
    @ResponseBody
    public String updateRecipients(@RequestBody  Recipients recipients) {
        try{
            Map mps = new HashMap();
            mps.put("aid",recipients.getAid());
            mps.put("status1","01");
            List<Recipients> acquisition = recipientsService.queryFilter(mps);
            if(acquisition.size()>0 && !acquisition.get(0).getId().equals(recipients.getId())){
                return "202";
            }
            recipientsService.updateRecipients(recipients);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 按照ID查询
     */
    @RequestMapping( "/findRecipientsById")
    @ResponseBody
    public Recipients findRecipientsById(String id,Model model,HttpServletRequest request) {
        Recipients recipients= recipientsService.findRecipientsById(id);
        return recipients;
    }


}
