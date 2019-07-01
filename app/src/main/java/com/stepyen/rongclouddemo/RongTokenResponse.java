package com.stepyen.rongclouddemo;

/**
 * date：2019/3/22
 * author：stepyen
 * description：融云获取token
 */
public class RongTokenResponse {
    /**
     * {"code":200,"userId":"123","token":"H47sZDKHjX30Hg5lsZ+1MiVfpXmDMDd84w1rNc1rABaJvZWn44PV5E8oOIH96cjAZ8yPoadK/70KsMbgYsrDHQ=="}
     */
    public String userId;
    public String token;
    public int code;

    public boolean isSuccess() {
        return 200 == code;
    }
}
