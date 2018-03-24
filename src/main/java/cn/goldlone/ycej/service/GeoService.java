package cn.goldlone.ycej.service;

import cn.goldlone.ycej.mapper.AllMapper;
import cn.goldlone.ycej.monitor.ChealPoint;
import cn.goldlone.ycej.po.GPSInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Created by CN on 2018/3/23 17:55 .
 */
@Service
public class GeoService {

    @Autowired
    private AllMapper al;

    @Autowired
    private ChealPoint cp;

    /**
     * 记录数据
     * @param object
     * @return
     */
    public String receive(JSONObject object) {
        String username = object.getString("username");
        int week = object.getInt("week");
        double longitude = object.getDouble("longitude");
        double latitude = object.getDouble("latitude");
        String time_dur = null;
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(df.format(date));
        if(hour>=0 && hour<8)
            time_dur = "morn";
        else if(hour>=8 && hour<12)
            time_dur = "noon";
        else if(hour>=12 && hour<18)
            time_dur = "after";
        else
            time_dur = "night";

        JSONObject res = new JSONObject();
        if(al.insertGPSInfo(new GPSInfo(username, longitude, latitude, new java.sql.Date(System.currentTimeMillis()), time_dur, week))>0) {
            res.put("res", true);
        }else {
            res.put("res", false);
        }

        return res.toString();
    }


    /**
     * 训练
     * @param object
     * @return
     */
    public String train(JSONObject object) {
        String username = object.getString("username");
        boolean isTrain = false;
//        ChealPoint cp = new ChealPoint();
        isTrain = cp.train(username);
        JSONObject res = new JSONObject();
        res.put("isTrain", isTrain);
        res.put("res", true);
        return res.toString();
    }


    /**
     * 检测轨迹点是否异常
     * @param object
     * @return
     */
    public String detect(JSONObject object) {
        String username = object.getString("username");
        int week = object.getInt("week");
        double longitude = object.getDouble("longitude");
        double latitude = object.getDouble("latitude");
        boolean isUsual = false;
        String time_dur = null;
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(df.format(date));
        if(hour>=0 && hour<8)
            time_dur = "morn";
        else if(hour>=8 && hour<12)
            time_dur = "noon";
        else if(hour>=12 && hour<18)
            time_dur = "after";
        else
            time_dur = "night";
//        ChealPoint cp = new ChealPoint();
        isUsual = cp.detect(username, week, longitude, latitude, time_dur);
        JSONObject res = new JSONObject();
        res.put("isUsual", isUsual);
        res.put("res", true);
        return res.toString();
    }
}
