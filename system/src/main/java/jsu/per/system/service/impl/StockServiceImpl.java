package jsu.per.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import jsu.per.system.service.StockService;

import jsu.per.system.utils.OKHTTPUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StockServiceImpl implements StockService {

    /**
     * 仅返回股票名和股票代码
     * @return
     * @throws IOException
     */
    @Override
    public JSONObject getAllStock() throws IOException {
        JSONObject jsonObject = OKHTTPUtil.GET("https://api.doctorxiong.club/v1/stock/all");
        return jsonObject;
    }

    @Override
    public JSONObject getStockMin(String code) throws IOException {
        String url = "https://api.doctorxiong.club/v1/stock/min?code="+code;
        JSONObject jsonObject = OKHTTPUtil.GET(url);
        return jsonObject;

    }

    @Override
    public JSONObject getKLineOfDay(String code, String startDate, String endDate, int type ) throws IOException {

        String url = "https://api.doctorxiong.club/v1/stock/kline/day?code="+code;
        if(startDate != null){
            url = url + "&startDate=" + startDate;
        }
        if(endDate != null){
            url = url + "&endDate=" + endDate;
        }
        if (type != 0){
            url = url + "&type=" + type;
        }
        JSONObject jsonObject = OKHTTPUtil.GET(url);
        return jsonObject;
    }

    @Override
    public JSONObject getKLineOfWeek(String code, String startDate, String endDate, int type) throws IOException {
        String url = "https://api.doctorxiong.club/v1/stock/kline/week?code="+code;
        if(startDate != null){
            url = url + "&startDate=" + startDate;
        }
        if(endDate != null){
            url = url + "&endDate=" + endDate;
        }
        if (type != 0){
            url = url + "&type=" + type;
        }
        JSONObject jsonObject = OKHTTPUtil.GET(url);
        return jsonObject;
    }

    @Override
    public JSONObject getKLineOfMonth(String code, String startDate, String endDate, int type) throws IOException {
        String url = "https://api.doctorxiong.club/v1/stock/kline/month?code="+code;
        if(startDate != null){
            url = url + "&startDate=" + startDate;
        }
        if(endDate != null){
            url = url + "&endDate=" + endDate;
        }
        if (type != 0){
            url = url + "&type=" + type;
        }
        JSONObject jsonObject = OKHTTPUtil.GET(url);
        return jsonObject;
    }

    //获取全部指数
    @Override
    public JSONObject getAllIndex() throws IOException {
        JSONObject jsonObject = OKHTTPUtil.GET("https://api.doctorxiong.club/v1/stock/index/all");
        return jsonObject;
    }

    @Override
    public JSONObject getStock(String code) throws IOException {
        String url = "https://api.doctorxiong.club/v1/stock?code="+code;
        return OKHTTPUtil.GET(url);
    }

    @Override
    public JSONObject getStockRank(JSONObject jsonObject) throws IOException {
        String url = "https://api.doctorxiong.club/v1/stock/rank";
        JSONObject json = OKHTTPUtil.POST(url, jsonObject);
        return json;
    }

    @Override
    public JSONObject getIndustry() throws IOException {
        String url = "https://api.doctorxiong.club/v1/stock/industry/rank";
        JSONObject json = OKHTTPUtil.GET(url);
        return json;
    }
}
