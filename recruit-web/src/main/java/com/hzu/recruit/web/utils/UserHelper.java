package com.hzu.recruit.web.utils;

import com.hzu.recruit.common.model.User;
import com.hzu.recruit.common.result.ResultMsg;
import org.apache.commons.lang3.StringUtils;

public class UserHelper {

    //根据注册信息提交的数据来判断信息是否准确
    public static ResultMsg validate(User account) {
        if( StringUtils.isBlank(account.getEmail())) {
            return ResultMsg.errorMsg("Email 有误");
        }

        if( StringUtils.isBlank(account.getName())) {
            return ResultMsg.errorMsg(" 名字有误");
        }

        if( StringUtils.isBlank(account.getConfirmPasswd()) || StringUtils.isBlank(account.getPasswd()) || !account.getPasswd()
                .equals(account.getConfirmPasswd()) ) {
            return ResultMsg.errorMsg(" 确认密码不一致");
        }

        if( account.getPasswd().length() > 6) {
            return ResultMsg.errorMsg("密码大于六位");
        }

        return ResultMsg.successMsg("");
    }

}
