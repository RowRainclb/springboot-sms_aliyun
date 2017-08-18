package com.k2data.k2app.service; 

import com.k2data.k2app.validate.SmsMessage;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
/** 
* SmsService Tester. 
* 
* @author <clb> 
* @since <pre>八月 18, 2017</pre> 
* @version 1.0 
*/ 
@RunWith(SpringRunner.class)
@Rollback
@SpringBootTest
public class SmsServiceTest { 

    @Autowired
    private SmsService testObject;
    
    @Before
    public void before() throws Exception { 
    } 
    
    @After
    public void after() throws Exception { 
    } 
    
        /** 
    * 
    * Method: sendSms(SmsMessage smsMessage) 
    * 
    */ 
    @Test
    public void testSendSms() throws Exception {
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setSignName("xx");
        smsMessage.setPhoneNumber("180xx");
        smsMessage.setTemplateCode("SMS_xxx");
        testObject.sendSms(smsMessage);
    } 
    
        /** 
    * 
    * Method: checkPhoneFormat(String phoneNumber) 
    * 
    */ 
    @Test
    public void testCheckPhoneFormat() throws Exception { 
    //TODO: Test goes here... 
    } 
    
        
    } 
