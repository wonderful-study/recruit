package com.hzu.recruit.web.controller;

import com.hzu.recruit.biz.service.UserService;
import com.hzu.recruit.common.constants.CommonConstants;
import com.hzu.recruit.common.model.User;
import com.hzu.recruit.common.result.ResultMsg;
import com.hzu.recruit.common.utils.HashUtil;
import com.hzu.recruit.web.utils.UserHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册提交：1.注册验证 2.发送邮件 3.邮件失败重定向到注册页面
     * 注册页获取：根据account对象为依据判断是否注册页获取请求
     * @param account
     * @param modelMap
     * @return
     */

    @RequestMapping("/accounts/register")
    public String accountsRegister(User account, ModelMap modelMap) {
        if (account == null || account.getName() == null) {
            return "user/accounts/register";
        }

        //用户验证
        ResultMsg resultMsg = UserHelper.validate(account);
        if( resultMsg.isSuceess() && userService.addAccount(account) ) {
            modelMap.put("email",account.getEmail());
            return "user/accounts/registerSubmit";
        }else {
            return "redirect:/accounts/register?" + resultMsg.asUrlParams();
        }
    }

    @RequestMapping("/accounts/verify")
    public String verify(String key) {
        boolean result = userService.enable(key);
        if(result) {
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        }else {
            return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期");
        }
    }


    //--------------------------------------登录流程--------------------------------------------------
    /**
     * 登录接口
     */
    @RequestMapping("/accounts/signin")
    public String signin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String passwd = request.getParameter("passwd");
        String target = request.getParameter("target");
        if(username == null || passwd == null) {
            request.setAttribute("target",target);
            return "/user/accounts/signin";
        }
        User user = userService.auth(username,passwd);
        if(user == null) {
            return "redirect:/accounts/signin?" + "target=" + target + "&username=" +username + "&"
                    + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        }else {
            HttpSession session = request.getSession(true);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE,user);
            session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE,user);
            return StringUtils.isNoneBlank(target)?"redirect" + target :"redirect:/index";
        }
    }

    /**
     * 登出操作
     * @param request
     * @return
     */
    @RequestMapping("/accounts/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();
        return "redirect:/index";
    }

    //------------------------个人信息页-----------------------------------

    /**
     * 1.提供信息页面
     * 2.更新用户信息
     * @param updateUser
     * @param modelMap
     * @return
     */
    @RequestMapping("/accounts/profile")
    public String profile(HttpServletRequest request,User updateUser,ModelMap modelMap) {
        if(updateUser.getEmail() == null) {
            return "/user/accounts/profile";
        }

        //更新用户信息
        userService.updateUser(updateUser,updateUser.getEmail());
        User query = new User();
        query.setEmail(updateUser.getEmail());
        List<User> users = userService.getUserByQuery(query);
        request.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE,users.get(0));
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }

    //更新密码
    @RequestMapping("/accounts/changePassword")
    public String changePaaword(String email,String passwd,String newPasswd,String confirmPasswd,ModelMap modelMap) {
        User user = userService.auth(email,passwd);
        if (user == null || !confirmPasswd.equals(newPasswd)) {
            return "redirect:/accounts/profile?" + ResultMsg.errorMsg("密码错误").asUrlParams();
        }
        User updateUser = new User();
        updateUser.setPasswd(HashUtil.encryPassword(newPasswd));
        userService.updateUser(updateUser,updateUser.getEmail());
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }
}
