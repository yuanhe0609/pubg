package com.dataAnalysis.pubgSeasonService.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PostApiUtil {
    public Map<String, Object> postApi(String postUrl,String method) {
        Map<String, Object> result = new HashMap<>();
        result.put("errorMsg", "");
        result.put("resultData", "");
        try {
            URL url = new URL(postUrl);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            // 设置请求方法和属性
            conn.setRequestMethod(method);
            conn.setRequestProperty("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxOTMwYjBiMC04YzY4LTAxM2UtM2YzZS01MjQxZDA2Y2U0NDgiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNzYwNTgyNzI4LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6Ii00NmUzNDk4YS0xNTUxLTQ4MjgtOTFiMi1iYTE2NGNhYzAxMTcifQ.UEaqG7q9WkbUdlt3iEGn1Eb6IQiT46mbpKkshvjgdTE");
            conn.setRequestProperty("Accept", "application/vnd.api+json");
            // 检查HTTP响应码
            int responseCode = conn.getResponseCode();
            result.put("responseCode", responseCode);
            JSONObject jsonObject = new JSONObject();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    // 解析JSON响应
                    if (response.length() > 0) {
                        jsonObject = JSON.parseObject(response.toString());
                    }
                }
            } else {
                // 处理错误响应
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        errorResponse.append(line);
                    }
                    result.put("errorMsg", "HTTP Error " + responseCode + ": " + errorResponse.toString());
                }
            }
            result.put("resultData", jsonObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
