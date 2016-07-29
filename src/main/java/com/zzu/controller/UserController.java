package com.zzu.controller;

import com.zzu.dto.Result;
import com.zzu.model.*;
import com.zzu.model.Collection;
import com.zzu.service.ResumeService;
import com.zzu.service.UserService;
import com.zzu.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2016/3/1.
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private ResumeService resumeService;

    /**
     * 学生用户登录
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Result login(String username, String password, boolean on,
                        HttpSession session, HttpServletResponse response) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<String, Object>();
        User user = userService.search(username, password);

        if (user != null) {
            if (on) {
                setCookie("cookie_user", response, username, password);
            }

            session.setAttribute(Common.USER, user);
        } else {
            result.setSuccess(false);
            result.setError("用户名或密码错误");
        }

        return result;
    }

    private void setCookie(String name, HttpServletResponse response, String username, String password) {
        Cookie cookie = new Cookie(name, username + "-" + password);
        cookie.setMaxAge(60 * 60 * 24 * 7); //cookie 保存7天
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 学生用户个人资料
     *
     * @param session
     * @return
     */
    @RequestMapping("/info")
    public String info(HttpSession session) {
        User user = (User) session.getAttribute(Common.USER);
        if (user == null) {
            return "redirect:/login";
        }

        return "info";
    }

    /**
     * 个人简历
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/resume")
    public String resume(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Common.USER);
        if (user == null) {
            return "redirect:/login";
        }

        return "resume";
    }

    @RequestMapping(value = "/resume/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Result getResumeById(@PathVariable("id") int id) {
        Result result = new Result();
        Resume resume = resumeService.getByUid(id);
        result.getData().put("resume",resume);
        return result;
    }

    /**
     * 账号设置
     *
     * @param session
     * @return
     */
    @RequestMapping("/setting")
    public String setting(HttpSession session) {
        User user = (User) session.getAttribute(Common.USER);
        if (user == null) {
            return "redirect:/login";
        }

        return "setting";
    }

    /**
     * 隐私设置
     *
     * @param session
     * @return
     */
    @RequestMapping("/secret")
    public String secret(HttpSession session) {
        User user = (User) session.getAttribute(Common.USER);
        if (user == null) {
            return "redirect:/login";
        }
        user = userService.getById(user.getId());
        session.setAttribute(Common.USER, user);

        return "secret";
    }

    /**
     * 我的收藏
     *
     * @param session
     * @return
     */
    @RequestMapping("/collection")
    public String collection(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Common.USER);
        if (user == null) {
            return "redirect:/login";
        }

        Result<Collection> result = userService.searchCollections(user.getId(), 1, Common.COUNT);
        model.addAttribute("collections", result.getList());

        return "collection";
    }
}
