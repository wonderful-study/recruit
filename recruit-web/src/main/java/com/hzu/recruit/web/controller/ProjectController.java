package com.hzu.recruit.web.controller;

import com.hzu.recruit.biz.service.AgencyService;
import com.hzu.recruit.biz.service.CityService;
import com.hzu.recruit.biz.service.ProjectService;
import com.hzu.recruit.biz.service.RecommendService;
import com.hzu.recruit.common.constants.CommonConstants;
import com.hzu.recruit.common.constants.ProjectUserType;
import com.hzu.recruit.common.model.*;
import com.hzu.recruit.common.page.PageData;
import com.hzu.recruit.common.page.PageParams;
import com.hzu.recruit.common.result.ResultMsg;
import com.hzu.recruit.web.interceptor.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private CityService cityService;

    /**
     * 1.实现分页
     * 2.支持小区搜索、类型搜索
     * 3.支持排序
     * 4.支持展示图片、地址、价格、标题等信息
     * @return
     */
    @RequestMapping("/project/list")
    public String projectList(Integer pageSize, Integer pageNum, Project query, ModelMap modelMap) {
        PageData<Project> ps = projectService.queryProject(query,PageParams.build(pageSize,pageNum));
        List<Project> hotProjects = recommendService.getHotProject(CommonConstants.RECOM_SIZE);
        modelMap.put("recomProjects",hotProjects);
        modelMap.put("ps",ps);
        modelMap.put("vo",query);
        return "project/listing";
    }

    /**
     * 1.查询项目详情
     * 2.查询关联的人事
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("project/detail")
    public String projectDetail(Long id,ModelMap modelMap) {
        //通过前端传的id查询出项目
        Project project = projectService.queryOneProject(id);
        ProjectUser projectUser = projectService.getProjectUser(id);
        //点击一次就增加这个id对应的分数，增加热度
        recommendService.increase(id);
        if (projectUser.getUserId() != null && !projectUser.getUserId().equals(0)) {
            //通过项目里的userId查询出用户信息
            modelMap.put("agent",agencyService.getAgentDetail(project.getUserId()));
        }
        List<Project> rcProjects = recommendService.getHotProject(CommonConstants.RECOM_SIZE);
        modelMap.put("recomProjects",rcProjects);
        modelMap.put("project",project);
        return "/project/detail";
    }

    /**
     * 用户可以发送邮件给人事，发送完重定向回这个页面
     * @param userMsg
     * @return
     */
    @RequestMapping("project/leaveMsg")
    public String projectMsg(UserMsg userMsg) {
        projectService.addUserMsg(userMsg);
        return "redirect:/project/detail?id=" + userMsg.getProjectId();
    }


    /**
     * 查询所有的城市，查询所有的区域，返回到前端给表单使用
     * @param map
     * @return
     */
    @RequestMapping("project/toAdd")
    public String toAdd(ModelMap map,Area area) {
        List<City> cities = cityService.getAllCities();
        List<Area> areas = projectService.getAllArea(area);
        map.put("cities",cities);
        map.put("areas",areas);
        map.put("vo",area);
        return "project/add";
    }


    /**
     * 1.获取用户
     * 2.设置项目状态。添加完即上架
     * 3.添加项目
     * @param project
     * @return
     */
    @RequestMapping("project/add")
    public String doAdd(Project project) {
        User user = UserContext.getUser();
        project.setState(CommonConstants.PROJECT_STATE_UP);
        projectService.addProject(project,user);
        return "redirect:/project/ownlist";
    }


    /**
     * 1.获取当前登录的用户信息
     * 2.通过用户id查询到此用户所发布的项目
     * 3.返回给前台页面
     * @param project
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @RequestMapping("project/ownlist")
    public String ownlist(Project project,Integer pageNum,Integer pageSize,ModelMap modelMap) {
        User user = UserContext.getUser();
        project.setUserId(user.getId());
        project.setBookmarked(false);
        modelMap.put("ps",projectService.queryProject(project,PageParams.build(pageSize,pageNum)));
        modelMap.put("pageType","own");
        return "project/ownlist";
    }



    //1.评分
    @ResponseBody
    @RequestMapping("project/rating")
    public ResultMsg projectRate(Double rating,Long id) {
        projectService.updateRating(id,rating);
        return ResultMsg.successMsg("ok");
    }

    //2.收藏
    @ResponseBody
    @RequestMapping("project/bookmark")
    public ResultMsg bookmark(Long id) {
        User user = UserContext.getUser();
        projectService.bindUserToProject(id,user.getId(),true);
        return ResultMsg.successMsg("ok");
    }

    //3.删除收藏
    @ResponseBody
    @RequestMapping("project/unbookmark")
    public ResultMsg unbookmark(Long id) {
        User user = UserContext.getUser();
        projectService.unbindUserToProject(id,user.getId(), ProjectUserType.BOOKMARK);
        return ResultMsg.successMsg("ok");
    }

    //删除发布的项目
    @RequestMapping(value = "project/del")
    public String delSale(Long id,String pageType) {
        User user = UserContext.getUser();
        projectService.unbindUserToProject(id,user.getId(),pageType.equals("own")?ProjectUserType.SALE:ProjectUserType.BOOKMARK);
        return "redirect:/project/ownlist";
    }


    //4.收藏列表
    @RequestMapping("project/bookmarked")
    public String bookmarked(Project project,Integer pageNum,Integer pageSize,ModelMap modelMap) {
        User user = UserContext.getUser();
        project.setBookmarked(true);
        project.setUserId(user.getId());
        modelMap.put("ps",projectService.queryProject(project,PageParams.build(pageSize,pageNum)));
        modelMap.put("pageType","book");
        return "/project/ownlist";
    }

}









