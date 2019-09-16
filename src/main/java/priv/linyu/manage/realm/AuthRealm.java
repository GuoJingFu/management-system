package priv.linyu.manage.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import priv.linyu.manage.pojo.User;
import priv.linyu.manage.service.IResourceService;
import priv.linyu.manage.service.IUserService;

import java.util.List;

/**
 * @className: AuthRealm
 * @author: QiuShangLin
 * @description:
 * @date: 2019/7/19 0019 0:08
 * @version: 1.0
 **/
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    private IResourceService resourceService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        User user = (User) principalCollection.getPrimaryPrincipal();
        if (user != null) {
            // 给资源授权
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            // 根据获得认证的用户编号查询该用户具备的资源URL集合
            List<String> resourceUrls = resourceService.findUrlByUserid(user.getUserid());
            // 遍历集合，组装成满足授权过滤器过滤格式，并添加到资源信息中
            resourceUrls.forEach(e -> info.addStringPermission(e));
            return info;
        }

        return null;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 获取令牌
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        User user = userService.findByUsername(token.getUsername());
        if (user != null) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                    user,
                    user.getPassword(),
                    ByteSource.Util.bytes(user.getUsername()),
                    getName()
            );
            return info;
        }

        return null;
    }

    /**
     * 清空缓存
     * @param principals
     */
    @Override
    protected void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
}
