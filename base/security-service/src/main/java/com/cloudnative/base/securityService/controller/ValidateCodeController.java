package com.cloudnative.base.securityService.controller;

import com.cloudnative.base.securityService.service.ValidateCodeService;
import com.cloudnative.logService.annotation.LogAnnotation;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * 验证码提供
 * @author zlt
 * @date 2018/12/18
 */
@Controller
public class ValidateCodeController {
    @Autowired(required = false)
    private Producer producer;

    @Autowired(required = false)
    private ValidateCodeService validateCodeService;

    /**
     * 创建验证码
     *
     * @throws Exception
     */
    @GetMapping("/validata/code/{deviceId}")
    @LogAnnotation(module="auth-server",recordRequestParam=false)
    public void createCode(@PathVariable String deviceId, HttpServletResponse response) throws Exception {
        Assert.notNull(deviceId, "机器码不能为空");
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        validateCodeService.saveImageCode(deviceId, text);
        try (
                ServletOutputStream out = response.getOutputStream()
                ) {
            ImageIO.write(image, "JPEG", out);
        }
    }

   
}
