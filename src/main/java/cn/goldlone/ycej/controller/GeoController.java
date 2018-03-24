package cn.goldlone.ycej.controller;

import cn.goldlone.ycej.service.GeoService;
import cn.goldlone.ycej.utils.IOUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Created by CN on 2018/3/23 12:06 .
 */
@RestController
public class GeoController {

    @Autowired
    private GeoService gs;


    /**
     * 记录数据
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/rec")
    public String receiveGPS(HttpServletRequest request) throws IOException {
        String jsonString = IOUtil.streamToString(request.getInputStream());
        JSONObject object = new JSONObject(jsonString);
        return gs.receive(object);
    }


    /**
     * 训练
     * @param request
     * @return
     */
    @PostMapping("/train")
    public String train(HttpServletRequest request) throws IOException {
        String jsonString = IOUtil.streamToString(request.getInputStream());
        JSONObject object = new JSONObject(jsonString);
        return gs.train(object);
    }


    /**
     * 检测轨迹点是否异常
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/detect")
    public String detect(HttpServletRequest request) throws IOException {
        String jsonString = IOUtil.streamToString(request.getInputStream());
        JSONObject object = new JSONObject(jsonString);
        return gs.detect(object);
    }

    /**
     * 处理异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        JSONObject res = new JSONObject();
        res.put("res", false);
        res.put("msg", e.getMessage());
        e.printStackTrace();
        return res.toString();
    }

}
