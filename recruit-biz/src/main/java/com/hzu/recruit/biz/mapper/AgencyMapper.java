package com.hzu.recruit.biz.mapper;

import com.hzu.recruit.common.model.Company;
import com.hzu.recruit.common.model.User;
import com.hzu.recruit.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AgencyMapper {

    List<User> selectAgent(@Param("user")User user, @Param("pageParams")PageParams pageParams);

    //查询人事的数量
    Long selectAgentCount(@Param("user")User user);

    //查询公司
    List<Company> selectAgency(@Param("company")Company company, @Param("pageParams")PageParams pageParams);

    //查询公司数量
    Long selectAgencyCount(@Param("company")Company company);
}
