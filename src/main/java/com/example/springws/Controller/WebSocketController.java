package com.example.springws.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *
 * @author yangyibo
 * @date 16/12/29
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class WebSocketController {

    @RequestMapping(value = "/websocket")
    public String websocket(HttpServletRequest request, HttpSession session){
        String browser = "xxx";
        //取出session中的browser
        Object sessionBrowser = session.getAttribute("browser");
        if (sessionBrowser == null) {
            System.out.println("不存在session，设置browser=" + browser);
            session.setAttribute("browser", browser);
        } else {
            System.out.println("存在session，browser=" + sessionBrowser.toString());
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + " : " + cookie.getValue());
            }
        }
        return  "websocket";
    }

}
