package com.hzu.recruit.common.result;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.eclipse.jetty.util.UrlEncoded;

import java.util.Map;

//用户注册返回的信息
//通过UserHelper的验证之后，会返回errorMsg与successMsg的值
public class ResultMsg {

    private static final String errorMsgKey = "errorMsg";     //在模板中得到成功或者失败信息

    private static final String successMsgKey = "successMsg";

    private String errorMsg;

    private String successMsg;


    //如果errorMsg的值是空的说明验证成功
    public  boolean isSuceess() {
        return errorMsg == null;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public static ResultMsg errorMsg(String msg) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setErrorMsg(msg);
        return resultMsg;
    }

    public static ResultMsg successMsg(String msg) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccessMsg(msg);
        return resultMsg;
    }

    public Map<String,String> asMap() {
        Map<String,String> map = Maps.newHashMap();
        map.put(successMsgKey,successMsg);
        map.put(errorMsgKey,errorMsg);
        return map;
    }

    //把resultMsg（两个参数）转化成url的形式
    public String asUrlParams() {
        Map<String,String> map = asMap();
        Map<String,String> newMap = Maps.newHashMap();
        map.forEach((k,v) -> {if(v != null) newMap.put(k, UrlEncoded.encodeString(v));});
        return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newMap);
    }
}
