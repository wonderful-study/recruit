package com.hzu.recruit.biz.service;

import com.google.common.collect.Lists;
import com.hzu.recruit.biz.mapper.UserMapper;
import com.hzu.recruit.common.model.User;
import com.hzu.recruit.common.utils.BeanHelper;
import com.hzu.recruit.common.utils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private MailService mailService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserMapper userMapper;

    public List<User> getUsers() {
        return userMapper.selectUserList();
    }


    /**
     * 1.把用户信息插入数据库，非激活;密码加盐md5，保存头像到本地
     * 2.生成key，绑定email
     * 3.发送邮件给用户
     * @param account
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(User account) {
        //对用户密码进行加盐操作
        account.setPasswd(HashUtil.encryPassword(account.getPasswd()));
        List<String> imgList = fileService.getImgPath(Lists.newArrayList(account.getAvatarFile()));
        if (!imgList.isEmpty()) {
            account.setAvatar(imgList.get(0));
        }
        //设置默认属性
        BeanHelper.setDefaultProp(account,User.class);
        //设置创建时间
        BeanHelper.onInsert(account);
        account.setEnable(0);
        //往数据库插入用户信息
        userMapper.insert(account);
        //发送邮件
        mailService.registerNotify(account.getEmail());
        return true;
    }


    public boolean enable(String key) {
        return mailService.enable(key);
    }

    //验证账户密码是否正确
    public User auth(String username, String passwd) {
        User user = new User();
        user.setEmail(username);
        user.setPasswd(HashUtil.encryPassword(passwd));
        user.setEnable(1);
        List<User> list = getUserByQuery(user);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public List<User> getUserByQuery(User user) {
        List<User> list = userMapper.selectUsersByQuery(user);
        return list;
    }

    public void updateUser(User updateUser, String email) {
        updateUser.setEmail(email);
        BeanHelper.onUpdate(updateUser);
        userMapper.updateByEmail(updateUser);
    }
}
