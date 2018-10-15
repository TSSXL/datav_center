package com.smart.cityos.datav.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2018-10-12.
 */
public class Ping {
    public static boolean ping(String ipAddress) throws Exception {
        int  timeOut =  3000 ;  //超时应该在3钞以上
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);     // 当返回值是true时，说明host是可用的，false则不可。
        return status;
    }



    public static Map windowsPing(String ipAddress, int pingTimes, int timeOut) {
        BufferedReader in = null;
        //返回状态，延迟
        Map statusMap=new HashMap();
        statusMap.put("time","-1");
        statusMap.put("status",false);

        Runtime r = Runtime.getRuntime();  // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut;
        try {   // 执行命令并获取输出
            System.out.println(pingCommand);
            Process p = r.exec(pingCommand);
            if (p == null) {
                return statusMap;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("GBK")));   // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            int connectedCount = 0;
            String line = null;
            String time="";
            while ((line = in.readLine()) != null) {
                System.out.println(line);

                if(line.indexOf("平均 =")!=-1){
                    int index=line.indexOf("平均 =");
                    int indexUnit=line.indexOf("ms",index);
                    statusMap.put("time",line.substring(index+4,indexUnit));
                }
                connectedCount += getCheckResult(line);
            }   // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            statusMap.put("status",connectedCount == pingTimes);
            return statusMap;
        } catch (Exception ex) {
            ex.printStackTrace();   // 出现异常则返回假
            return statusMap;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map linuxPing(String ipAddress, int pingTimes) {
        BufferedReader in = null;
        //返回状态，延迟
        Map statusMap=new HashMap();
        statusMap.put("time","-1");
        statusMap.put("status",false);

        Runtime r = Runtime.getRuntime();  // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + ipAddress + " -c " + pingTimes;
        try {   // 执行命令并获取输出
            System.out.println(pingCommand);
            Process p = r.exec(pingCommand);
            if (p == null) {
                return statusMap;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("GBK")));   // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            int connectedCount = 0;
            String line = null;
            String time="";
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                int ind=line.indexOf("min/avg/max");
                if(ind>-1){
                    String useTimeLine=line.substring(ind);
                    System.out.println(useTimeLine);
                    String[] useTimeArr=useTimeLine.split("=");
                    System.out.print(useTimeArr);
                    String[] titleArr=useTimeArr[0].split("/");
                    System.out.print(titleArr);
                    String[] valueArr=useTimeArr[1].split("/");
                    System.out.print(valueArr);
                    int avgIndex=0;
                    //获取平均时间下标
                    for (int i=0;i<titleArr.length;i++){
                        if("avg".equals(titleArr[i])){
                            avgIndex=i;
                            break;
                        }
                    }
                    String avgValue=valueArr[avgIndex];
                    System.out.println(avgValue);

                }

                connectedCount += getCheckResult(line);
            }   // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            statusMap.put("status",connectedCount == pingTimes);
            return statusMap;
        } catch (Exception ex) {
            ex.printStackTrace();   // 出现异常则返回假
            return statusMap;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
    private static int getCheckResult(String line) {  // System.out.println("控制台输出的结果为:"+line);
        Pattern pattern =  java.util.regex.Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }



}
