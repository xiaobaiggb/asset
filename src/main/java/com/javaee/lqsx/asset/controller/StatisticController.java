package com.javaee.lqsx.asset.controller;

import com.javaee.lqsx.asset.po.Recipients;
import com.javaee.lqsx.asset.service.AssetsService;
import com.javaee.lqsx.asset.po.Assets;
import com.javaee.lqsx.asset.po.Manage;
import com.javaee.lqsx.asset.service.RecipientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 控制层
 */

@Controller
@RequestMapping(value = "/statistic")
public class StatisticController {

    // 依赖注入
    @Autowired
    private AssetsService assetsService;
    @Autowired
    private RecipientsService recipientsService;

    @RequestMapping(value = "/statistic")
    public String statistic(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("ad") == null){
            session.setAttribute("msg", "对不起，请登录！");
            return "login";
        }
        Manage manage = (Manage)session.getAttribute("ad");
        //资产状态占比
        List<String> itemz = new ArrayList<>();
        Map mp1= new HashMap();
        mp1.put("mid",manage.getId());
        StringBuilder strz = new StringBuilder("[");
        List<Assets> assets = assetsService.queryFilter(mp1);
        Map<String, List<Assets>> map = assets.stream().collect( Collectors.groupingBy( a -> a.getStatus() ));
        int num = 0;
        for(String k:map.keySet()){
            itemz.add("'"+k+"'");
            num ++;
            if (num == map.keySet().size()) {
                strz.append("{value:").append(map.get(k).size()).append(",name:'").append(k).append("'}");
            }else{
                strz.append("{value:").append(map.get(k).size()).append(",name:'").append(k).append("'},");
            }
        }
        strz.append("]");
        model.addAttribute("strz",strz);
        model.addAttribute("itemz",itemz);
        //资产领用占比
        List<String> items = new ArrayList<>();
        StringBuilder strs = new StringBuilder("[");
        Map mps = new HashMap();
        mps.put("status1","01");
        mps.put("mid",manage.getId());
        List<Recipients> acquisition = recipientsService.queryFilter(mps);
        items.add("'已领用'");
        strs.append("{value:").append(acquisition.size()).append(",name:'").append("已领用").append("'},");
        List<Assets> assets1 = assetsService.queryFilter(mp1);
        items.add("'未领用'");
        strs.append("{value:").append(assets1.size()-acquisition.size()).append(",name:'").append("未领用").append("'}");
        strs.append("]");
        model.addAttribute("strs",strs);
        model.addAttribute("items",items);
        return "statistic";
    }



    // //去掉月份或天多余的0 ======2018-01-03 ===>  2018-1-3
    private int simplify(String date){
        int index2 =date.lastIndexOf("-");
        int day;
        if(date.substring(index2+1,index2+2).equals("0")){
            day= Integer.parseInt(date.substring(index2+2,index2+3));
        }else{
            day= Integer.parseInt(date.substring(index2+1,index2+3));
        }
        return  day;
    }

    /**
     * 获取过去7天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public List<String> getDays(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = intervals; i > 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     * @param past
     * @return
     */
    public String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }


    /**
     *字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
}
