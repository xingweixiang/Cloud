package com.cloudnative.base.securityService.controller;

import com.cloudnative.base.securityService.service.ValidateCodeService;
import com.cloudnative.base.support.util.StringUtils;
import com.cloudnative.base.support.web.Result;
import com.cloudnative.logService.annotation.LogAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信提供
 */
@RestController
public class SmsController {

    public final static String SYSMSG_LOGIN_PWD_MSG="验证码：{0}，3分钟内有效";

    @Autowired(required = false)
    private ValidateCodeService validateCodeService;

    @RequestMapping("/sms/send")
    @LogAnnotation(module="oauth",recordRequestParam=false)
    public Result sendSms(@RequestParam(value = "mobile",required = false) String mobile) {
        String content = SmsController.SYSMSG_LOGIN_PWD_MSG.replace("{0}", StringUtils.generateRamdomNum());
//        SendMsgResult sendMsgResult = MobileMsgConfig.sendMsg(mobile, content);

        String calidateCode = StringUtils.generateRamdomNum();

        // TODO: 2019-08-29 发送短信验证码 每个公司对接不同，自己实现

        validateCodeService.saveImageCode(mobile, calidateCode);
        return  Result.succeed(  calidateCode, "发送成功");
    }

}
