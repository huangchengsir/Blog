package com.huang.config.shiro;

import com.huang.Dao.UserMapper;
import com.huang.pojo.User;
import com.huang.service.Imp.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.Md5CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

@Slf4j
public class Realme extends AuthorizingRealm {
    @Autowired
    private UserServiceImp userServiceImp;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //授权
        //访问了授权url页面时会走此方法，这一个添加需要配置筛选添加，否则对所有用户都授权info.addObjectPermission("xxx");
        //获取当前登录对象
//        Subject subject = SecurityUtils.getSubject();
        //获取从认证中传输过来的对象
//        Object principal = subject.getPrincipal();
        //查询出数据库中存取的授权信息，通过add添加对应权限，即可做到权限控制url
//        info.addObjectPermission(principal.getPerms());
        //必须返回info授权才会成功
        return null;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usertoken = (UsernamePasswordToken) token;
        User user = userServiceImp.searchByname(usertoken.getUsername());
        if (user == null){
            return null;
        }
        String name = user.getUsername();
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).toUpperCase();
        log.info(user.toString());
        if (!usertoken.getUsername().equals(name)){
            return null;
        }
        return new SimpleAuthenticationInfo(user,password,"MyRealm");
    }
}
