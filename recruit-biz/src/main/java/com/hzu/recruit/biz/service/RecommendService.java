package com.hzu.recruit.biz.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.hzu.recruit.common.model.Project;
import com.hzu.recruit.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendService {

    @Autowired
    ProjectService projectService;

    private static final String HOT_PROJECT_KEY = "hot_projects";

    public void increase(Long id) {
        Jedis jedis = new Jedis("127.0.0.1");
        //通过热度排序，1.0代表增加的分数，保存一个元素，每个元素对应一个分数
        jedis.zincrby(HOT_PROJECT_KEY,1.0D,id+"");
        //排序之后删除10以后的数据元素
        jedis.zremrangeByRank(HOT_PROJECT_KEY,10,-1);
        jedis.close();
    }

    /**
     * 返回热门项目的projectId
     * @return
     */
    public List<Long> getHot() {
        Jedis jedis = new Jedis("127.0.0.1");
        //将元素列表取出来，按热度高到低排序
        Set<String> idSet = jedis.zrevrange(HOT_PROJECT_KEY,0,-1);
        jedis.close();
        List<Long> ids = idSet.stream().map(Long::parseLong).collect(Collectors.toList());
        return ids;
    }

    /**
     * 查询项目的详情,将项目按照从高到低的评分（热度）来排序
     * @param size
     * @return
     */
    public List<Project> getHotProject(Integer size) {
        Project query = new Project();
        List<Long> list = getHot();
        list = list.subList(0,Math.min(list.size(),size));
        if (list.isEmpty()) {
            return Lists.newArrayList();
        }
        query.setIds(list);
        final List<Long> orderList = list;
        List<Project> projects = projectService.queryAndSetImg(query, PageParams.build(size,1));

        //一个排序的函数
        Ordering<Project> projectSort = Ordering.natural().onResultOf(hs ->{
            //根据下标进行排序
            return orderList.indexOf(hs.getId());
        });
        return projectSort.sortedCopy(projects);
    }

    /**
     * 通过创建时间进行倒序，把项目从数据库查询出来
     * @return
     */
    public List<Project> getLastest() {
        Project query = new Project();
        query.setSort("create_time");
        List<Project> projects = projectService.queryAndSetImg(query,PageParams.build(8,1));
        return projects;
    }
}
