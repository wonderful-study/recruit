package com.hzu.recruit.biz.mapper;

import com.hzu.recruit.common.model.*;
import com.hzu.recruit.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectMapper {

    List<Project> selectPageProjects(@Param("project")Project project, @Param("pageParams")PageParams pageParams);

    //根据某条件查询返回项目的总数
    Long selectPageCount(@Param("project")Project query);

    //根据项目名称或者区域id查询出area
    List<Area> selectArea(Area area);

    //将userMsg添加进表中
    int insertUserMsg(UserMsg userMsg);

    //查询用户与项目是否有绑定关系，是发布项目还是收藏项目
    ProjectUser selectProjectUser(@Param("userId")Long userId,@Param("projectId")Long projectId,@Param("type")Integer type);

    //查询对应的项目关系
    ProjectUser selectSaleProjectUser(@Param("projectId")Long projectId);

    //根据userId与type，查询某个用户所发布的项目
    List<Long> selectProjectIdByUserId(@Param("userId")Long userId,@Param("type")Integer type);

    //根据前台表单提交的项目信息，将其添加至数据库
    int insertProject(Project project);

    //根据前台提交的信息填充ProjectUser类并添加至数据库
    int  insertProjectUser(ProjectUser projectUser);

    //查询城市
    List<City> selectCity(City city);

    //更新项目的评分
    int updateProjectRating(Project updateProject);

    //解除项目与用户的绑定
    //超过一个参数要加@Param注解
    int deleteProjectUser(@Param("id")Long id, @Param("userId")Long userId, @Param("type")Integer value);

    int deleteProjectById(Long id);


    List<ProjectUser> selectProjectUserByProjectId(Long id);

    //根据项目id来删除有这个项目关联的ProjectUser
    void deleteProjectUserByProjectId(Long projectId);
}
