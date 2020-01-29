package com.bat.shop.api.shiro.ream;

import com.bat.qmall.utils.StringUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 11:52
 * @function:
 */
public class MyRealm extends AuthorizingRealm {


	/**
	 * 授权
	 *
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		//从PrincipalCollection中获取登录用户的信息
		//利用登录的用户信息来给用户分配角色或权限
		//创建SimpleAuthorizationInfo ，并设置其roles

		//Set<String> roles = adminService.getRolesByAccount(principals.toString());
		//System.out.println("roles = " + roles);

		//return new SimpleAuthorizationInfo(roles);
		return null;
	}

	/**
	 * 认证
	 *
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		//强转token
		/*UsernamePasswordToken user = (UsernamePasswordToken)token;

		//从数据库查询数据
		ShopAdmin shopAdmin = adminService.getUserByAcount(user.getUsername());

		if(shopAdmin==null){
			throw new UnknownAccountException("用户不存在");
		}
		//如果用户被锁定
		if(shopAdmin.getAstatus() == AdminConstants.STATUE_ADMIN_DISABLE){
			throw new LockedAccountException("用户被锁定");
		}

		//这里不需要判断密码是否正确，因为整个认证过程需要交给shiro去完成
		//我们只要做用户是否存在，是否被锁定即可，剩下的密码校验交给shiro

		*//*
		 *传入三个参数
		 *
		 * principal：认证的实体类信息  可以是username  也可以是数据表对应的实体类对象
		 * credentials
		 * realmName
		 *//*
		String principal = user.getUsername();	//账号
		String credentials = shopAdmin.getApass();	//数据库获取的密码
		String realmName = super.getName();	//reaml的名字

		ByteSource salt = ByteSource.Util.bytes(principal);

		return new SimpleAuthenticationInfo(principal,credentials,salt,realmName);*/
		return null;
	}
}
