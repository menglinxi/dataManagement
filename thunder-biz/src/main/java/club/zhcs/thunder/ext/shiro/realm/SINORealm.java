package club.zhcs.thunder.ext.shiro.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.nutz.lang.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import club.zhcs.thunder.bean.acl.User;
import club.zhcs.thunder.bean.acl.User.Status;
import club.zhcs.thunder.biz.acl.ShiroUserService;
import club.zhcs.thunder.ext.shiro.matcher.SINOCredentialsMatcher;
/**
 * 
 * @author kerbores@gmail.com
 *
 */
@Component
public class SINORealm extends AuthorizingRealm {

	@Autowired
	SINOCredentialsMatcher sinoCredentialsMatcher;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.shiro.realm.AuthenticatingRealm#getCredentialsMatcher()
	 */
	@Override
	public CredentialsMatcher getCredentialsMatcher() {
		return sinoCredentialsMatcher;
	}

	@Autowired
	ShiroUserService shiroUserService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.realm.AuthenticatingRealm#supports(org.apache.shiro.authc.
	 * AuthenticationToken)
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String userName = upToken.getUsername();
		User user = shiroUserService.findByName(userName);
		if (Lang.isEmpty(user))// 用户不存在
			return null;
		if (user.getStatus() == Status.DISABLED)// 用户被锁定
			throw new LockedAccountException("Account [" + upToken.getUsername() + "] is locked.");

		SimpleAuthenticationInfo account = new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());

		return account;
	}
	


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String userName = principalCollection.getPrimaryPrincipal().toString();
		User user = shiroUserService.findByName(userName);
		if (user == null)// 用户不存在
			return null;
		if (user.getStatus() == Status.DISABLED)// 用户被锁定
			throw new LockedAccountException("Account [" + userName + "] is locked.");
		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
		List<String> roleNameList = shiroUserService.getRolesInfo(user.getId());
		auth.addRoles(roleNameList);// 添加角色
		List<String> permissionNames = shiroUserService.getAllPermissionsInfo(user.getId());
		auth.addStringPermissions(permissionNames);// 添加权限
		return auth;
	}

}
