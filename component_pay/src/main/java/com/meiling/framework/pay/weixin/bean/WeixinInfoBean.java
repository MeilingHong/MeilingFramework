package com.meiling.framework.pay.weixin.bean;


public class WeixinInfoBean {
    private String openId;
    private String code;
    private String secretKey;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        return "WeixinInfoBean{" +
                "openId='" + openId + '\'' +
                ", code='" + code + '\'' +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }
}
