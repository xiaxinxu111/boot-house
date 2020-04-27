package com.etoak.controller;

import com.etoak.commons.VerifyCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class CodeController {

    @GetMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        VerifyCode verifyCode = new VerifyCode();
        //获取图片返回到前端页面
        BufferedImage image = verifyCode.getImage();
        //图片表达式结果 保存到本次session中
        int result = verifyCode.getResult();

        request.getSession().setAttribute("code",result);
        response.setHeader("Pragma","No-Cache");
        response.setHeader("Cache-Control","No-Cache");
        response.setHeader("Expires","0");
        response.setContentType("image/jpeg");
        ImageIO.write(image,"JPEG",response.getOutputStream());

    }
}
