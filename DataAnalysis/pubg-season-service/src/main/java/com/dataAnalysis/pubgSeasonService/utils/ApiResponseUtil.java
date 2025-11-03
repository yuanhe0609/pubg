package com.dataAnalysis.pubgSeasonService.utils;

import com.alibaba.fastjson2.JSONObject;

import java.util.Map;

public class ApiResponseUtil {
    public static JSONObject checkResultMap(Map<String, Object> result) {
        Object responseCodeObj = result.get("responseCode");
        if (responseCodeObj == null) {
            throw new RuntimeException("API响应码为空");
        }
        if (!responseCodeObj.equals(200)) {
            throw new RuntimeException("API调用失败，响应码: " + responseCodeObj + "，" + result.get("errorMsg"));
        }
        Object resultDataObj = result.get("resultData");
        if (resultDataObj == null) {
            throw new RuntimeException("API返回数据为空");
        }
        JSONObject resultData = JSONObject.parseObject(resultDataObj.toString());
        if (resultData == null) {
            throw new RuntimeException("JSON解析失败: resultData");
        }
        return resultData;
    }
}