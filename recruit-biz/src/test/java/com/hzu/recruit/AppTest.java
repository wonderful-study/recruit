package com.hzu.recruit;

import com.hzu.recruit.biz.mapper.ProjectMapper;
import com.hzu.recruit.common.model.Area;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest
{

    @Autowired
    private ProjectMapper projectMapper;

    @Test
    @Ignore
    public void testProject() {
        Area area = new Area();
        area.setName("java开发");
        List<Area> areas = projectMapper.selectArea(area);
        System.out.println(areas.get(0));
    }


}

