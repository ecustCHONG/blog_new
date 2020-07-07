package com.star.config;

import com.star.po.User;
import com.star.service.UserService;
import com.star.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
    自定义realm
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权 AuthorizationInfo");
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
////        info.addStringPermission("user:add");
//        Subject subject = SecurityUtils.getSubject();
//        User currentUser = (User) subject.getPrincipal();
//        if (currentUser.getPerms()!=null && currentUser.getPerms().contains("user:add")) {
//            info.addStringPermission("user:add");
//            return info;
//        }else {
//            info.addStringPermission("no");
//            return info;
//        }
        return null;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了=>认证 AuthenticationInfo");

        //连接真实数据库
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        User user = userService.checkByUsername(userToken.getUsername());
        if (user==null){
            return null;//不存在该用户,unknownAccountWxception
        }else{
            String password = user.getPassword();
            //密码认证，shiro,固定代码
            return new SimpleAuthenticationInfo(user, password,"");
        }


//        String name="root";
//        String password= "123456";
//        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
//        if (!userToken.getUsername().equals(name)){
//            return null;//抛出异常， unknownAccountWxception
//        }

    }
}
