package com.sfpy.controller;/**
 * @author zhangk
 * @date 2017/11/15 13:04
 */

import com.sfpy.util.VerifyCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *验证码图片
 *@author sfpy
 *@create 13:04
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("verif.do")
    public void createVerifitionCode(HttpServletRequest request, String f, HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入会话session
        HttpSession session = request.getSession(true);
        //删除以前的
        session.removeAttribute("verfitionCode");
        session.setAttribute("verfitionCode", verifyCode.toLowerCase());
        //生成图片
        int w = 150, h = 50;
        try {
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //验证验证码
    @ResponseBody
    @RequestMapping("verifitionIsTrue.do")
    public boolean isVerifitionCode(HttpServletRequest request,String verfitionCode) {
        HttpSession session = request.getSession(true);
        String verifitoinCode =(String)session.getAttribute("verfitionCode");
        boolean flag = false;
        if(verifitoinCode != null && verifitoinCode.equalsIgnoreCase(verfitionCode)){
            flag=true;
        }
        return flag;
    }
}
