package com.huang.config.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

public class Realme extends AuthorizingRealm {

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
        String name = "admin";
        String password = "123456";
        UsernamePasswordToken usertoken = (UsernamePasswordToken) token;

        if (!usertoken.getUsername().equals(name)){
            return null;
        }
        return new SimpleAuthenticationInfo("",password,"");
    }
}
