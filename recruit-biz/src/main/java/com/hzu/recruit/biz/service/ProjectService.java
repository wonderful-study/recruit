package com.hzu.recruit.biz.service;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.hzu.recruit.biz.mapper.ProjectMapper;
import com.hzu.recruit.common.constants.ProjectUserType;
import com.hzu.recruit.common.model.*;
import com.hzu.recruit.common.page.PageData;
import com.hzu.recruit.common.page.PageParams;
import com.hzu.recruit.common.utils.BeanHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Value("${file.prefix}")
    private String imgPrefix;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private MailService mailService;

    @Autowired
    private FileService fileService;

    /**
     * 1.查询区域
     * 2.添加图片服务地址前缀
     * 3.构建分页结果
     * @param query
     * @param pageParams
     */
    public PageData<Project> queryProject(Project query, PageParams pageParams) {
        List<Project> projects = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(query.getName())) {
            Area area = new Area();
            area.setName(query.getName());
            List<Area> areas = projectMapper.selectArea(area);
            if (!areas.isEmpty()) {
                query.setAreaId(areas.get(0).getId());
            }
        }
        projects = queryAndSetImg(query,pageParams);
        Long count = projectMapper.selectPageCount(query);
        return PageData.buildPage(projects,count,pageParams.getPageSize(),pageParams.getPageNum());
    }

    public List<Project> queryAndSetImg(Project query, PageParams pageParams) {
        List<Project> projects = projectMapper.selectPageProjects(query,pageParams);
        projects.forEach(h ->{
            //h.setFirstImg(imgPrefix + h.getFirstImg());
            //h.setImageList(h.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
            h.setFirstImg(h.getFirstImg());
            h.setImageList(h.getImageList().stream().map(img -> img).collect(Collectors.toList()));
        });
        return projects;
    }

    /**
     * 根据人事id来查询出项目
     * @param id
     * @return
     */
    public Project queryOneProject(Long id) {
        Project query = new Project();
        query.setId(id);
        List<Project> projects = queryAndSetImg(query,PageParams.build(1,1));
        if (!projects.isEmpty()) {
            return projects.get(0);
        }
        return null;
    }

    /**
     * 1.把userMsg的字段补充完整
     * 2.把数据加入数据库
     * 3.给人事发送邮件
     * @param userMsg
     */
    public void addUserMsg(UserMsg userMsg) {
        BeanHelper.onInsert(userMsg);
        projectMapper.insertUserMsg(userMsg);
        User agent = agencyService.getAgentDetail(userMsg.getAgentId());
        mailService.sendMail("来自用户"+userMsg.getEmail()+"的留言",userMsg.getMsg(),agent.getEmail());
    }

    public ProjectUser getProjectUser(Long projectId) {
        return projectMapper.selectSaleProjectUser(projectId);
    }

    /**
     * 根据userId与type，查询某个用户所发布的项目,返回一个项目id的集合
     * @param userId
     * @param type
     * @return
     */
    public List<Long> selectProjectIds(Long userId, Integer type) {
        List<Long> ids = projectMapper.selectProjectIdByUserId(userId,type);
        return ids;
    }

    public List<Area> getAllArea(Area area) {
        return projectMapper.selectArea(area);
    }

    /**
     * 1.添加项目图片
     * 2.插入项目信息
     * 3.绑定用户到项目的关系
     * @param project
     * @param user
     */
    public void addProject(Project project, User user) {
        if (CollectionUtils.isNotEmpty(project.getProjectFiles())) {
            //返回来的是一个List<String>的多个图片地址，用Jioner把它以逗号分割成一个字符串
            String images = Joiner.on(",").join(fileService.getImageList(project.getProjectFiles()));
            project.setImages(images);
        }
        //设置创建时间
        BeanHelper.onInsert(project);
        //把项目信息插入数据库
        projectMapper.insertProject(project);
        bindUserToProject(project.getId(),user.getId(),false);
    }

    //绑定项目与用户的关系，是哪个用户发布或者收藏这个项目，bookmarked：是否收藏
    public void bindUserToProject(Long projectId, Long userId, boolean bookmarked) {
        ProjectUser existProjectUser = projectMapper.selectProjectUser(userId,projectId,bookmarked? ProjectUserType.BOOKMARK.value:ProjectUserType.SALE.value);
        if (existProjectUser != null) {
            return;
        }
        ProjectUser projectUser = new ProjectUser();
        projectUser.setProjectId(projectId);
        projectUser.setUserId(userId);
        projectUser.setType(bookmarked?ProjectUserType.BOOKMARK.value:ProjectUserType.SALE.value);
        //设置默认值
        BeanHelper.setDefaultProp(projectUser,ProjectUser.class);
        //设置创建时间
        BeanHelper.onInsert(projectUser);
        projectMapper.insertProjectUser(projectUser);
    }


    //更新项目的评分
    public void updateRating(Long id, Double rating) {
        Project project = queryOneProject(id);
        Double oldRating = project.getRating();
        //新评分等于新旧评分取平均值，最大值为5
        Double newRating = oldRating.equals(0D) ? rating : Math.min((oldRating+rating)/2,5);

        Project updateProject = new Project();
        updateProject.setId(id);
        updateProject.setRating(newRating);
        //更新时间
        BeanHelper.onUpdate(updateProject);
        projectMapper.updateProjectRating(updateProject);
    }


    /**
     * 1.把项目与用户的绑定信息删除
     * 2.如果该项目是发布类型的，则先删除收藏这个项目的用户项目绑定
     * 3.删除该项目
     * @param id
     * @param userId
     * @param type
     */
    public void unbindUserToProject(Long id, Long userId, ProjectUserType type) {
        projectMapper.deleteProjectUser(id,userId,type.value);
        if (type.value == 1) {
             List<ProjectUser> projectUsers = projectMapper.selectProjectUserByProjectId(id);
             projectUsers.forEach(projectUser -> projectMapper.deleteProjectUserByProjectId(projectUser.getProjectId()));
            projectMapper.deleteProjectById(id);
        }
    }
}
