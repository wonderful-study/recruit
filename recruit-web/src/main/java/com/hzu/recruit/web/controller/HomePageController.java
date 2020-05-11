package com.hzu.recruit.web.controller;

import com.hzu.recruit.biz.service.RecommendService;
import com.hzu.recruit.common.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomePageController {

    @Autowired
    RecommendService recommendService;

    /**
     * 查询最近发布的项目，返回到前台首页的新上项目模块
     * @param modelMap
     * @return
     */
    @RequestMapping("index")
    public String index(ModelMap modelMap) {
        List<Project> projects = recommendService.getLastest();
        modelMap.put("recomProjects",projects);
        return "/homepage/index";
    }

    /**
     * 根目录重定向到首页
     * @param modelMap
     * @return
     */
    @RequestMapping("")
    public String home(ModelMap modelMap) {
        return "redirect:/index";
    }

}
