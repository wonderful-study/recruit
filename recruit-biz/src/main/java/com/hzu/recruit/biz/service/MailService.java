package com.hzu.recruit.biz.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.hzu.recruit.biz.mapper.UserMapper;
import com.hzu.recruit.common.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${domain.name}")
    private String domainName;


    @Autowired
    private UserMapper userMapper;

    //从properties中设置的邮箱发送邮件到注册邮箱
    public void sendMail(String title, String url, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(email);
        message.setSubject(title);
        message.setText(url);
        mailSender.send(message);
    }

    /**
     * 1.缓存key-mial的关系
     * 2.借助spring mail 发送邮件
     * 3.借助异步框架进行异步操作
     * @Async: 执行此方法时spring会创建一个线程池将此方法放入线程池中
     * @param email
     */

    @Async
    public void registerNotify(String email) {
        //随机生成一个10位的字符串
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        //结合key，email，cache生成一个缓存
        registerCache.put(randomKey,email);
        String url = "http://" + domainName + "/accounts/verify?key=" + randomKey;
        //发送邮件
        sendMail("校企合作平台激活",url,email);

    }

    /**
     * 定义一个本地缓存
     * expireAfterAccess：设置过期时间
     * removalListener：把用户从数据库删除
     */
    private final Cache<String,String> registerCache = CacheBuilder.newBuilder().maximumSize(100)
            .expireAfterAccess(15, TimeUnit.MINUTES)
            .removalListener(new RemovalListener<String, String>() {

                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {
                    userMapper.deleteByEmail(notification.getValue());
                }
            }).build();

    //点击激活链接后更新enable的值为1
    public boolean enable(String key) {
        String email = registerCache.getIfPresent(key);
        if(StringUtils.isBlank(email)) {
            return  false;
        }
        User updateUser = new User();
        updateUser.setEmail(email);
        updateUser.setEnable(1);
        userMapper.updateByEmail(updateUser);
        registerCache.invalidate(key);
        return true;
    }
}
