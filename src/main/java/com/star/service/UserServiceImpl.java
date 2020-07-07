package com.star.service;

import com.star.dao.UserRepository;
import com.star.po.User;
import com.star.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: ONESTAR
 * @QQ群: 530311074
 * @URL: https://onestar.newstar.net.cn/
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

    @Override
    public void register(String username, String password) {
        User user=new User();
        user.setUsername(username);
        user.setPassword(MD5Utils.code(password));
        userRepository.save(user);

    }

    @Override
    public User checkByUsername(String username) {
        User user=userRepository.findByUsername(username);
        return user;
    }
}
