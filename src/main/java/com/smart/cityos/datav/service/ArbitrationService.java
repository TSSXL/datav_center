package com.smart.cityos.datav.service;

import com.smart.cityos.datav.domain.model.AQI7days;
import com.smart.cityos.datav.domain.model.AQITrend;
import com.smart.cityos.datav.service.feign.config.ConfigFeignService;
import com.smart.cityos.datav.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 仲裁委接口
 *
 * @author: mingcheong
 * @date Created in 2018-08-27
 */
@Slf4j
@Service
public class ArbitrationService {

    @Autowired
    private ConfigFeignService configFeignService;


    /**
     * 获取月均统计信息
     * @param data
     * @return
     */
    public Map getMonthPercent(Map data){
        //今日案源
        String jassql="select count(*) count from w_case_apply where DATE_FORMAT(F_case_endtime,'%Y')=DATE_FORMAT(NOW(),'%Y')";
        String sassql="select count(*) count from w_case_apply where DATE_FORMAT(F_create_time,'%Y')=DATE_FORMAT(NOW(),'%Y')";
        String wjasql="select count(*) count from w_case_apply where DATE_FORMAT(F_filing_time,'%Y')=DATE_FORMAT(NOW(),'%Y') and F_case_endtime is not null";
        String msssql="select count(*) count from bpm_node_user where CMPIDS ='005'";

        Map query=new HashMap();
        query.put("dbInfo",data.get("dbInfo"));
        query.put("sql",jassql);

        List<Map> jas=configFeignService.executeQuery(query);
        int jasCount=Integer.parseInt(jas.get(0).get("count").toString());

        query.put("sql",sassql);
        List<Map> sas=configFeignService.executeQuery(query);
        int sasCount=Integer.parseInt(sas.get(0).get("count").toString());

        query.put("sql",wjasql);
        List<Map> wja=configFeignService.executeQuery(query);
        int wjaCount=Integer.parseInt(wja.get(0).get("count").toString());

        query.put("sql",msssql);
        List<Map> mss=configFeignService.executeQuery(query);
        int mssCount=Integer.parseInt(mss.get(0).get("count").toString());

        Map result=new HashMap();
        result.put("jal",Math.round(jasCount/(sasCount+197)));
        if(jasCount==0){
            result.put("cagzl",0);
            result.put("tcl",0);
        }else{
            result.put("cagzl",Math.round(wjaCount/jasCount));
            result.put("tcl",Math.round(0/jasCount));
        }

        result.put("msjas",Math.round(jasCount/mssCount));


        return result;
    }

    /**
     *获取今日统计信息
     * @param data
     * @return
     */
    public List<Map> getTodayCountByType(Map data){
        //今日案源
        String aysql="select count(*) count from w_case_apply where date_format(F_create_time,'%Y-%m-%d')=date_format(now(),'%Y-%m-%d')";
        //今日立案
        String lasql="select count(*) count from w_case_apply where date_format(F_filing_time,'%Y-%m-%d')=date_format(now(),'%Y-%m-%d')";
        //今日结案
        String jasql="select count(*) count from w_case_apply where date_format(F_case_endtime,'%Y-%m-%d')=date_format(now(),'%Y-%m-%d')";

        Map query=new HashMap();
        query.put("dbInfo",data.get("dbInfo"));
        if("ay".equals(data.get("type"))){
            query.put("sql",aysql);
        }else if("la".equals(data.get("type"))){
            query.put("sql",lasql);
        }else if("ja".equals(data.get("type"))){
            query.put("sql",jasql);
        }


        List<Map> result=configFeignService.executeQuery(query);


        return result;
    }

    /**
     * 获取平均时间统计
     * @param data
     * @return
     */
    public List<Map> getAvgTime(Map data){
        String statDayName=data.get("statDayName").toString();
        String endDayName=data.get("endDayName").toString();
        String startMonth=String.valueOf(data.get("startMonth"));
        String startMonthSql="";
        if(!"null".equals(startMonth) && !"".equals(startMonth)){
            startMonthSql="and c.start_time_>='"+startMonth+"' ";
        }
        String sql="select COALESCE(CONCAT(round(avg(TIMESTAMPDIFF(DAY ,c.START_TIME_,d.END_TIME_)),2),'','DAY'),0) avgTime " +
                "from w_case_apply a,bpm_pro_run b,act_hi_actinst c,act_hi_actinst d " +
                "where a.flowRunId_=b.RUNID " +
                "and b.ACTINSTID=c.PROC_INST_ID_ " +
                "and c.ACT_NAME_='"+statDayName+"' " +startMonthSql+
                "and not exists(select 0 from act_hi_actinst c1 where b.ACTINSTID=c1.PROC_INST_ID_ " +
                "and c1.ACT_NAME_='"+statDayName+"' and c1.start_time_>c.START_TIME_) " +
                "and b.ACTINSTID=d.PROC_INST_ID_ " +
                "and d.ACT_NAME_='"+endDayName+"' " +
                "and not exists(select 0 from act_hi_actinst d1 where b.ACTINSTID=d1.PROC_INST_ID_ " +
                "and d1.ACT_NAME_='"+endDayName+"' and d1.start_time_>d.START_TIME_)";

        Map query=new HashMap();
        query.put("dbInfo",data.get("dbInfo"));
        query.put("sql",sql);

        List<Map> result=configFeignService.executeQuery(query);
        return result;
    }

    /**
     * 获取秘书办案统计
     * @param data
     */
    public List<Map> MssCaseCount(Map data){
        Map query=new HashMap();
        query.put("dbInfo",data.get("dbInfo"));
        //获取所有秘书
        String msSql="select IFNULL(F_case_decretary_id,'空') F_case_decretary_id,IFNULL(F_case_decretary,'空') F_case_decretary from w_case_apply group by F_case_decretary_id,F_case_decretary";
        query.put("sql",msSql);
        List<Map> msRe=configFeignService.executeQuery(query);

        //统计秘书存案数
        String cas="select count(*) count,IFNULL(F_case_decretary_id,'空') F_case_decretary_id from w_case_apply where DATE_FORMAT(F_acceptance_time,'%Y-%M')!=DATE_FORMAT(NOW(),'%Y-%M') and F_case_endtime is null  group by F_case_decretary_id ";
        query.put("sql",cas);
        List<Map> casRe=configFeignService.executeQuery(query);

        //统计秘书本月收案数
        String sas="select count(*) count,IFNULL(F_case_decretary_id,'空') F_case_decretary_id from w_case_apply where DATE_FORMAT(F_acceptance_time,'%Y-%M')=DATE_FORMAT(NOW(),'%Y-%M')  group by F_case_decretary_id";
        query.put("sql",sas);
        List<Map> sasRe=configFeignService.executeQuery(query);

        //统计秘书本月结案数
        String jas="select count(*) count,IFNULL(F_case_decretary_id,'空') F_case_decretary_id from w_case_apply where DATE_FORMAT(F_case_endtime,'%Y-%M')=DATE_FORMAT(NOW(),'%Y-%M')  group by F_case_decretary_id";
        query.put("sql",jas);
        List<Map> jasRe=configFeignService.executeQuery(query);

        //统计秘书本月未结数
        String wjs="select count(*) count,IFNULL(F_case_decretary_id,'空') F_case_decretary_id from w_case_apply where F_case_endtime is null  group by F_case_decretary_id";
        query.put("sql",wjs);
        List<Map> wjsRe=configFeignService.executeQuery(query);
        //统计秘书本月调解数(全是0)

        //融合字段值
        List<Map> result=makeBaseCount(msRe,casRe,"cas");
        result=makeBaseCount(result,sasRe,"sas");
        result=makeBaseCount(result,jasRe,"jas");
        result=makeBaseCount(result,wjsRe,"wjs");
        result=makePercentCount(result);

        return result;
    }

    public List<Map> makeBaseCount(List<Map> base,List<Map> value,String name){
        for (int i=0;i<base.size();i++){
            Map ba=base.get(i);

            ba.put(name,0);
            for(int k=0;k<value.size();k++){
                //秘书ID相同时，赋值
                if(ba.get("F_case_decretary_id").equals(value.get(k).get("F_case_decretary_id"))){
                    ba.put(name,Integer.parseInt(value.get(k).get("count").toString()));
                    break;
                }
            }
        }

        return base;
    }

    public List<Map> makePercentCount(List<Map> base){
        for (int i=0;i<base.size();i++){
            Map ba=base.get(i);
            ba.put("tjs",0);
            ba.put("jal","0%");
            int jas=Integer.parseInt(ba.get("jas").toString());
            int sas=Integer.parseInt(ba.get("sas").toString());
            int cas=Integer.parseInt(ba.get("cas").toString());
            if((sas+cas)!=0){
                ba.put("jal",Math.round((jas/(sas+cas))*100)+"%");
            }

        }

        return base;
    }

    /**
     * 获取秘书办案图表统计
     * @param data
     */
    public Map MssCaseBarCount(Map data){
        Map query=new HashMap();
        query.put("dbInfo",data.get("dbInfo"));
        //获取所有秘书
        String baseSql="select IFNULL(F_case_decretary_id,'空') F_case_decretary_id,IFNULL(F_case_decretary,'空') F_case_decretary from w_case_apply group by F_case_decretary_id,F_case_decretary";


        //统计秘书存案数
        String cas="select count(*) count,IFNULL(F_case_decretary_id,'空') F_case_decretary_id " +
                "from w_case_apply " +
                "where DATE_FORMAT(F_acceptance_time,'%Y-%M')!=DATE_FORMAT(NOW(),'%Y-%M') " +
                "and F_case_endtime is null  " +
                "group by F_case_decretary_id";

        //统计秘书本月收案数
        String sas="select count(*) count,IFNULL(F_case_decretary_id,'空') F_case_decretary_id from w_case_apply where DATE_FORMAT(F_acceptance_time,'%Y-%M')=DATE_FORMAT(NOW(),'%Y-%M')  group by F_case_decretary_id";


        //统计秘书本月结案数
        String jas="select count(*) count,IFNULL(F_case_decretary_id,'空') F_case_decretary_id from w_case_apply where DATE_FORMAT(F_case_endtime,'%Y-%M')=DATE_FORMAT(NOW(),'%Y-%M')  group by F_case_decretary_id";


        //统计秘书本月未结数
        String wjs="select count(*) count,IFNULL(F_case_decretary_id,'空') F_case_decretary_id from w_case_apply where F_case_endtime is null  group by F_case_decretary_id";

        //统计秘书本月调解数(全是0)

        //融合字段值
        Map result=new HashMap();
        List<Map> list=new ArrayList<>();

        if("存案数".equals(data.get("type"))){
            String sql="select base.*,IFNULL(se.count,0) count from ("+baseSql+
                    ") base left join ("+cas+") se on base.F_case_decretary_id=se.F_case_decretary_id " +
                    "order by count asc";

            query.put("sql",sql);
            List<Map> re=configFeignService.executeQuery(query);
            result=makeBasePieCount(re);
        }else if("收案数".equals(data.get("type"))){
            String sql="select base.*,IFNULL(se.count,0) count from ("+baseSql+
                    ") base left join ("+sas+") se on base.F_case_decretary_id=se.F_case_decretary_id " +
                    "order by count asc";

            query.put("sql",sql);
            List<Map> re=configFeignService.executeQuery(query);
            result=makeBasePieCount(re);
        }else if("结案数".equals(data.get("type"))){
            String sql="select base.*,IFNULL(se.count,0) count from ("+baseSql+
                    ") base left join ("+jas+") se on base.F_case_decretary_id=se.F_case_decretary_id " +
                    "order by count asc";

            query.put("sql",sql);
            List<Map> re=configFeignService.executeQuery(query);
            result=makeBasePieCount(re);
        }else if("未结数".equals(data.get("type"))){
            String sql="select base.*,IFNULL(se.count,0) count from ("+baseSql+
                    ") base left join ("+wjs+") se on base.F_case_decretary_id=se.F_case_decretary_id " +
                    "order by count asc";

            query.put("sql",sql);
            List<Map> re=configFeignService.executeQuery(query);
            result=makeBasePieCount(re);
        }else if("调解数".equals(data.get("type"))){

            query.put("sql",baseSql);
            List<Map> re=configFeignService.executeQuery(query);
            for(int i=0;i<re.size();i++){
                re.get(i).put("count",0);
            }
            result=makeBasePieCount(re);
        }



        return result;
    }

    public Map makeBasePieCount(List<Map> list){
        String[] category=new String[list.size()];
        List<Map> data=new ArrayList<Map>();
        for(int i=0;i<list.size();i++){
            category[i]=list.get(i).get("F_case_decretary").toString();
            Map map=new HashMap();
            map.put("value",list.get(i).get("count"));
            data.add(map);
        }
        Map result=new HashMap();
        result.put("category",category);
        result.put("data",data);

        return result;
    }

    /**
     * 获取根据标的金额统计立案
     * @param data
     */
    public List<Map> CountLasByAmonut(Map data){
        Map query=new HashMap();
        query.put("dbInfo",data.get("dbInfo"));

        String sql1="select IFNULL(count(*),0) count from w_case_apply where F_case_amount <=1000 and F_filing_time is not null";
        query.put("sql",sql1);
        List<Map> re1=configFeignService.executeQuery(query);

        String sql2="select IFNULL(count(*),0) count from w_case_apply where F_case_amount >1000 and F_case_amount<=50000 and F_filing_time is not null";
        query.put("sql",sql2);
        List<Map> re2=configFeignService.executeQuery(query);

        String sql3="select IFNULL(count(*),0) count from w_case_apply where F_case_amount >5000 and F_case_amount<=100000 and F_filing_time is not null";
        query.put("sql",sql3);
        List<Map> re3=configFeignService.executeQuery(query);

        String sql4="select IFNULL(count(*),0) count from w_case_apply where F_case_amount >100000 and F_filing_time is not null";
        query.put("sql",sql4);
        List<Map> re4=configFeignService.executeQuery(query);

        List<Map> result=new ArrayList<>();

        Map map1=new HashMap();
        map1.put("value",re1.get(0).get("count"));
        map1.put("name","≤1000元");
        Map map2=new HashMap();
        map2.put("value",re2.get(0).get("count"));
        map2.put("name","≤50000元");
        Map map3=new HashMap();
        map3.put("value",re3.get(0).get("count"));
        map3.put("name","≤100000元");
        Map map4=new HashMap();
        map4.put("value",re4.get(0).get("count"));
        map4.put("name","＞100000元");
        result.add(map1);
        result.add(map2);
        result.add(map3);
        result.add(map4);



        return result;
    }

    /**
     * 获取根据案源统计立案
     * @param data
     */
    public List<Map> CountLasByAYuan(Map data){
        Map query=new HashMap();
        query.put("dbInfo",data.get("dbInfo"));

        String sql="select IFNULL(count(*),0) value,IFNULL(F_case_source,'空') name " +
                "from w_case_apply where F_filing_time is not null " +
                "group by F_case_source";
        query.put("sql",sql);
        List<Map> re=configFeignService.executeQuery(query);
        List<Map> result=new ArrayList<>();
        String[] category=new String[re.size()];
        for(int i=0;i<re.size();i++){
            category[i]=re.get(i).get("name").toString();
        }
        Map map=new HashMap();
        map.put("category",category);
        result.add(map);
        Map map1=new HashMap();
        map1.put("data",re);
        result.add(map1);



        return result;
    }

    /**
     * 获取根据案源统计立案
     * @param data
     */
    public List<Map> CountLasByAYou(Map data){
        Map query=new HashMap();
        query.put("dbInfo",data.get("dbInfo"));

        String sql="select count(a.id) value,b.value_ name from w_case_apply a,(select * from w_dict where group_ ='cause') b " +
                "where " +
                "a.F_case_cause=b.key_ " +
                "and a.F_filing_time is not null " +
                "group by a.F_case_cause";
        query.put("sql",sql);
        List<Map> re=configFeignService.executeQuery(query);
        List<Map> result=new ArrayList<>();
        String[] category=new String[re.size()];
        for(int i=0;i<re.size();i++){
            category[i]=re.get(i).get("name").toString();
        }
        Map map=new HashMap();
        map.put("category",category);
        result.add(map);
        Map map1=new HashMap();
        map1.put("data",re);
        result.add(map1);



        return result;
    }
}