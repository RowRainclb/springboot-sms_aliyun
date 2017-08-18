package com.k2data.k2app.service;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import com.k2data.k2app.config.AliyunConfig;
import com.k2data.k2app.validate.SmsMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
@Service
public class SmsService {

    private AliyunConfig aliyunConfig;

    @Autowired
    public SmsService (AliyunConfig aliyunConfig) {
        this.aliyunConfig = aliyunConfig;
    }

    /**
     * 阿里发送短信接口
     * @param smsMessage
     * @return
     * @throws Exception
     */
    public String sendSms(SmsMessage smsMessage) throws Exception {
        //校验手机号是否合法
        if (smsMessage.getPhoneNumber() == null || !checkPhoneFormat(smsMessage.getPhoneNumber())) {
//            throw new PhoneNumberFormatException();
            throw new Exception();
        }

        CloudAccount account = new CloudAccount(aliyunConfig.getAccessKeyID(), aliyunConfig.getAccessKeySecret(), aliyunConfig.getSms().getEndPoint());
        MNSClient client = account.getMNSClient();
        CloudTopic topic = client.getTopicRef(aliyunConfig.getSms().getTopic());

        RawTopicMessage msg = new RawTopicMessage();
        msg.setMessageBody(aliyunConfig.getDefaultMessageBody());
//        List<Parameter> parameters = smsMessage.getParameters();
        //生成消息属性　
        MessageAttributes messageAttributes = new MessageAttributes();
        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
        // 设置签名
        batchSmsAttributes.setFreeSignName(smsMessage.getSignName());
        //设置发送短信使用的模板
        batchSmsAttributes.setTemplateCode(smsMessage.getTemplateCode());
        //设置模板中对应的参数值
        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
//        for (Parameter p : parameters) {
//            String parameterName = p.getName();
//            String parameterValue = p.getValue();
//            smsReceiverParams.setParam(parameterName, parameterValue);
//        }

        //增加接收短信的号码
        batchSmsAttributes.addSmsReceiver(smsMessage.getPhoneNumber(), smsReceiverParams);

        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);

        try {
            TopicMessage ret = topic.publishMessage(msg, messageAttributes);
//            log.info("MessageID : " + ret.getMessageId()+ "; Code : " +smsMessage.getParameters().get(0).getValue() + "; Phone number : "+smsMessage.getPhoneNumber());
            return ret.getMessageId();
        } catch (ServiceException se) {
            log.error(se.getMessage());
//            throw new SmsPublishException();
            throw new Exception();
        }finally {
            client.close();
        }

    }

    public boolean checkPhoneFormat(String phoneNumber){
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(14[579]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

}
