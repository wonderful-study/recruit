package com.hzu.recruit.web;

import com.hzu.recruit.biz.mapper.ProjectMapper;
import com.hzu.recruit.biz.mapper.UserMapper;
import com.hzu.recruit.common.model.Area;
import com.hzu.recruit.common.model.Project;
import com.hzu.recruit.common.model.ProjectUser;
import com.hzu.recruit.common.model.User;
import com.hzu.recruit.common.page.PageParams;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Test
    @Ignore
    public void projectsTest() {
        List<User> users = userMapper.selectUserList();
        System.out.println(users.get(0).getName());
    }

    @Test
    @Ignore
    public void testAreas() {
        Area area = new Area();
        area.setName("java开发");
        List<Area> areas = projectMapper.selectArea(area);
        System.out.println(areas.get(0).getName());
    }

    @Test
    @Ignore
    public void testCount() {
        Project project = new Project();
        project.setName("java");
        Long count = projectMapper.selectPageCount(project);
        System.out.println(count.toString());
    }

    @Test
    @Ignore
    public void testPage() {
        Project query = new Project();

        PageParams pageParams = new PageParams();
        pageParams.setPageNum(1);
        pageParams.setPageSize(2);
        pageParams.setLimit(2);
        pageParams.setOffset(0);

        List<Project> projects = projectMapper.selectPageProjects(query,pageParams);
        System.out.println(projects.get(0).getName());
    }

    @Test
    @Ignore
    public void testSelectProject() {
        ProjectUser projectUser = projectMapper.selectProjectUser(1L,45L,1);
        if (projectUser != null) {
            System.out.println("存在");
        }else {
            System.out.println("不存在");
        }
    }

}














