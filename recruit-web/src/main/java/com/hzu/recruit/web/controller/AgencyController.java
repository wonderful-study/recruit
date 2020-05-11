package com.hzu.recruit.web.controller;

import com.hzu.recruit.biz.service.AgencyService;
import com.hzu.recruit.biz.service.ProjectService;
import com.hzu.recruit.biz.service.RecommendService;
import com.hzu.recruit.common.constants.CommonConstants;
import com.hzu.recruit.common.model.Company;
import com.hzu.recruit.common.model.Project;
import com.hzu.recruit.common.model.ProjectUser;
import com.hzu.recruit.common.model.User;
import com.hzu.recruit.common.page.PageData;
import com.hzu.recruit.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RecommendService recommendService;

    /**
     * 查询出人事的数量，信息，返回给前台做分页列表展示
     * @param pageSize
     * @param pageNum
     * @param modelMap
     * @return
     */
    @RequestMapping("/agency/agentList")
    public String agentList(Integer pageSize, Integer pageNum, ModelMap modelMap) {
        //查询所有的人事信息
        PageData<User> ps = agencyService.getAllAgent(PageParams.build(pageSize,pageNum));
        modelMap.put("ps",ps);
        return "/user/agent/agentList";
    }


    /**
     * 1.人事的具体信息，包括人事资料，所发布的项目
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("agency/agentDetail")
    public String agentDetail(Long id,ModelMap modelMap) {
        //通过id查询用户表中的人事
        User user = agencyService.getAgentDetail(id);

        //先查询用户id来返回他所发布的项目的id的一个集合
        ProjectUser projectUser = new ProjectUser();
        projectUser.setUserId(id);
        projectUser.setType(1);
        List<Long>  ids = projectService.selectProjectIds(projectUser.getUserId(),projectUser.getType());

        Project query = new Project();
        query.setUserId(id);
        query.setBookmarked(false);
        query.setIds(ids);

        //通过项目id的集合返回一个项目的集合
        PageData<Project> bindProject = projectService.queryProject(query,new PageParams(3,1));
        if (bindProject != null) {
            modelMap.put("bindProjects",bindProject.getList());
        }
        //热门项目
        List<Project> rcProjects = recommendService.getHotProject(CommonConstants.RECOM_SIZE);
        modelMap.put("recomProjects",rcProjects);
        modelMap.put("agent",user);
        return "/user/agent/agentDetail";
    }

    /**
     * 查看公司列表
     * @return
     */
    @RequestMapping("agency/list")
    public String agencyList(Integer pageSize,Integer pageNum,ModelMap modelMap) {
        PageData<Company> ps = agencyService.getAllAgency(PageParams.build(pageSize,pageNum));
        modelMap.put("ps",ps);
        return "agency/list";
    }


    @RequestMapping("agency/agencyDetail")
    public String agencyDetail(Long id,ModelMap modelMap) {
        Company company = agencyService.getAgencyDetail(id);

        //先查询用户id来返回他所发布的项目的id的一个集合
        User agent = new User();
        agent.setEnable(1);
        agent.setType(2);
        agent.setCompanyId(id);

        PageData<User> bindUser = agencyService.getAllAgentDetail(agent,new PageParams(3,1));
        if (bindUser != null) {
            modelMap.put("bindUsers",bindUser.getList());
        }

        //热门项目
        List<Project> rcProjects = recommendService.getHotProject(CommonConstants.RECOM_SIZE);
        modelMap.put("recomProjects",rcProjects);
        modelMap.put("agency",company);
        return "/agency/agencyDetail";
    }
}
