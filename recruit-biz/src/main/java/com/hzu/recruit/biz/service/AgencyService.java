package com.hzu.recruit.biz.service;

import com.hzu.recruit.biz.mapper.AgencyMapper;
import com.hzu.recruit.common.model.Company;
import com.hzu.recruit.common.model.User;
import com.hzu.recruit.common.page.PageData;
import com.hzu.recruit.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyService {

    @Autowired
    private AgencyMapper agencyMapper;

    @Value("${file.prefix}")
    private String imgPrefix;

    /**
     * 访问user表获取详情
     * @param userId
     * @return
     */
    public User getAgentDetail(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setType(2);
        List<User> list = agencyMapper.selectAgent(user, PageParams.build(1,1));
        setImg(list);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    private void setImg(List<User> list) {
        list.forEach(i ->{
            //i.setAvatar(imgPrefix + i.getAvatar());
            i.setAvatar(i.getAvatar());
        });
    }

    public PageData<User> getAllAgent(PageParams pageParams) {
     //查询所有的人事信息
     List<User> agents = agencyMapper.selectAgent(new User(),pageParams);
     setImg(agents);
     //查询人事的数量
     Long count = agencyMapper.selectAgentCount(new User());
     return PageData.buildPage(agents,count,pageParams.getPageSize(),pageParams.getPageNum());
    }

    //查询所有公司
    public PageData<Company> getAllAgency(PageParams pageParams) {
        List<Company> agencys = agencyMapper.selectAgency(new Company(),pageParams);
        Long count = agencyMapper.selectAgencyCount(new Company());
        return PageData.buildPage(agencys,count,pageParams.getPageSize(),pageParams.getPageNum());
    }

    //查询公司详情
    public Company getAgencyDetail(Long id) {
        Company company = new Company();
        company.setId(id);
        List<Company> list = agencyMapper.selectAgency(company,PageParams.build(1,1));
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public PageData<User> getAllAgentDetail(User agent, PageParams pageParams) {
        User user = new User();
        user.setCompanyId(agent.getCompanyId());
        user.setType(2);
        user.setEnable(agent.getEnable());
        List<User> list = agencyMapper.selectAgent(user,pageParams);
        Long count = agencyMapper.selectAgentCount(user);
        return PageData.buildPage(list,count,pageParams.getPageSize(),pageParams.getPageNum());
    }
}
