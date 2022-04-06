package com.zxy.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxy.entity.Stock;
import com.zxy.entity.StockSim;


public class JsonTO {
    public static Stock toStock(JSONObject json){
        String data = json.get("data").toString();
        JSONArray array = JSONObject.parseArray(data);
        Stock stock = new Stock();
        JSONObject jsonObject = (JSONObject) array.get(0);
        stock.setCode(jsonObject.get("code").toString());
        stock.setName(jsonObject.get("name").toString());
        stock.setType(jsonObject.get("type").toString());
        stock.setPriceChange(jsonObject.get("priceChange").toString());
        stock.setChangePercent(jsonObject.get("changePercent").toString());
        stock.setOpen(jsonObject.get("open").toString());
        stock.setClose(jsonObject.get("close").toString());
        stock.setPrice(jsonObject.get("price").toString());
        stock.setHigh(jsonObject.get("high").toString());
        stock.setLow(jsonObject.get("low").toString());
        stock.setVolume(jsonObject.get("volume").toString());
        stock.setTurnover(jsonObject.get("turnover").toString());
        stock.setTurnoverRate(jsonObject.get("turnoverRate").toString());
        stock.setTotalWorth(jsonObject.get("totalWorth").toString());
        stock.setCirculationWorth(jsonObject.get("circulationWorth").toString());
        stock.setDate(jsonObject.get("date").toString());
        stock.setPb(jsonObject.get("pb").toString());
        stock.setSpe(jsonObject.get("spe").toString());
        stock.setPe(jsonObject.get("pe").toString());


        String[] strings = jsonObject.get("buy").toString().split(",");

        for (int i = 0;i<10;i++){
            if (i == 0){
                strings[0] = strings[0].substring(2,strings[0].length()-1);
                continue;
            }
            if (i == 9){
                strings[9] = strings[9].substring(1,strings[9].length()-2);
                continue;
            }
            strings[i] = strings[i].substring(1,strings[i].length()-1);
        }

        stock.setBuy(strings);

        strings = jsonObject.get("sell").toString().split(",");

        for (int i = 0;i<10;i++){
            if (i == 0){
                strings[0] = strings[0].substring(2,strings[0].length()-1);
                continue;
            }
            if (i == 9){
                strings[9] = strings[9].substring(1,strings[9].length()-2);
                continue;
            }
            strings[i] = strings[i].substring(1,strings[i].length()-1);
        }

        stock.setSell(strings);

        return stock;
    }

    public static StockSim toStockSim(JSONObject json){
        String data = json.get("data").toString();
        if (data == null){
            return null;
        }
        JSONArray array = JSONObject.parseArray(data);
        StockSim stock = new StockSim();
        JSONObject jsonObject = (JSONObject) array.get(0);
        stock.setCode(jsonObject.get("code").toString());
        stock.setName(jsonObject.get("name").toString());
        return stock;
    }
}
