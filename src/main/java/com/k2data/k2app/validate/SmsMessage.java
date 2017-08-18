package com.k2data.k2app.validate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 阿里短信对象
 */
@Setter
@Getter
@ToString
public class SmsMessage {
    //接收验证码的手机号
    private String phoneNumber;

    //签名名称
    private String signName;

    //模板名称
    private String templateCode;

    //模板里面的参数
    private List<Parameter> parameters;
}
