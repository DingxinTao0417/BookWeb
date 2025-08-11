package com.example.book.app.utils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.example.book.app.domain.Wp;

import java.net.URLEncoder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class WpCode {
    public static String WpEncode(Wp wp) {
        String json = JSON.toJSONString(wp, JSONWriter.Feature.WriteEnumsUsingName);
        String b64 = Base64.getUrlEncoder().withoutPadding().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        return URLEncoder.encode(b64, StandardCharsets.UTF_8);
    }

    public static Wp WpDecode(String wpStr) {
        if (wpStr == null || wpStr.isEmpty()) return null;
        String b64 = URLDecoder.decode(wpStr, StandardCharsets.UTF_8);
        byte[] raw = Base64.getUrlDecoder().decode(b64);
        String json = new String(raw, StandardCharsets.UTF_8);
        return JSON.parseObject(json, Wp.class);
    }
}
