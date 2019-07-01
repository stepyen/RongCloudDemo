package com.stepyen.rongclouddemo;

import java.util.HashMap;
import java.util.Random;

/**
 * date：2019/3/22
 * author：stepyen
 * description：融云请求工具类
 */
public class RongRequestUtil {

    /**
     * 获取融云通用请求头
     *
     * @return
     */
    public static HashMap<String, String> getCommonHeaderMap() {

        HashMap<String, String> map = new HashMap<String, String>();
        final String nonce = Integer.toString(new Random().nextInt(1000));
        final String timeStamp = Long.toString(System.currentTimeMillis());
        final String signature = SHA1Tool.SHA1(Constant.KEY_SECRET_RONG_CLOUD + nonce + timeStamp);

        map.put("RC-App-Key", Constant.KEY_RONG_CLOUD);
        map.put("RC-Nonce", nonce);
        map.put("RC-Timestamp", timeStamp);
        map.put("RC-Signature", signature);

        return map;
    }


}
