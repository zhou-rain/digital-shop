package com.bat.qmall.plugin;

import com.alibaba.fastjson.JSON;
import com.bat.qmall.Const.UmsConst;
import com.bat.qmall.utils.Validator;
import com.bat.qmall.webUtils.HttpclientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhouR
 * @date: Create in 2020/1/29 - 22:43
 * @function: 新浪社交登录
 */
public class SinaUtil {

	private static final String APP_KEY= "1748603315";
	private static final String APP_SECRET= "71336ca9ca580b678aa6e94f99a8fd25";
	private static final String CALL_BACK_SUCCESS= "http://userpass.qmall.com:8882/sinalogin";		//回调成功地址
	private static final String CALL_BACK_FAIL= "http://userpass.qmall.com:8882/sinalogout";		//回调失败地址


	/**
	 * 请求申请授权的地址  获取第三方的code
	 * @param	APP_KEY   应用id
	 * @param	CALL_BACK_SUCCESS   成功回调函数
	 */
	public static final String GET_CODE_URL = "https://api.weibo.com/oauth2/authorize?client_id="+APP_KEY+"&response_type=code&redirect_uri="+CALL_BACK_SUCCESS;

	/**
	 * 获取access_token
	 * @param	client_id   		应用id
	 * @param	client_secret   	秘钥
	 * @param	grant_type   		请求方式，（固定）
	 * @param	redirect_uri   		成功回调函数
	 * @param	code   				授权码
	 *
	 */
	private static final String GET_ACCESS_TOKEN_URL = "https://api.weibo.com/oauth2/access_token";


	/**
	 * 用access_token查询用户信息
	 * @param	access_token
	 * @param	uid   				用户在新浪上的id
	 */
	private static final String  GET_MEMBER_INFO_BY_ACCESS_TOKEN_URL = "https://api.weibo.com/2/users/show.json";






	/**
	 *	根据用户授权码 code  获取 access_token 和 uid
	 * @return
	 */
	public static String getAccessTokenAndUidByCode(String code){

		String url = GET_ACCESS_TOKEN_URL;
		//?client_id="+APP_KEY+"&client_secret="+APP_SECRET+"&grant_type=authorization_code&redirect_uri="+CALL_BACK_SUCCESS+"&code=CODE"

		Map<String,String> param = new HashMap<>();
		param.put("client_id",APP_KEY);
		param.put("client_secret",APP_SECRET);
		param.put("grant_type","authorization_code");
		param.put("redirect_uri",CALL_BACK_SUCCESS);
		param.put("code",code);

		String resultMapJson = HttpclientUtil.doPost(url, param);
		//{"access_token":"2.00pjCAxGRbx1uBe4236e7ed9kVd_EB","remind_in":"157679999","expires_in":157679999,"uid":"6368611345","isRealName":"true"}

	/*	Map<String,String> map = JSON.parseObject(resultMapJson, Map.class);
		if(map==null){
			return "请求失败，授权码过期";
		}
		String access_token = map.get("access_token");
	*/
		return resultMapJson;
	}


	/**
	 * 根据access_token 获取用户信息
	 *
	 * @param access_token 上面方法获取到的
	 * @param uid          用户在新浪上的id
	 *
	 * 返回数据示例
	 * https://open.weibo.com/wiki/2/users/show
	 */
	public static Map<String,String> getMemberInfo(String access_token,String uid){

		String url = GET_MEMBER_INFO_BY_ACCESS_TOKEN_URL+"?access_token="+access_token+"&uid="+uid;

		String memberJson = HttpclientUtil.doGet(url);

		Map<String,String> user_map = JSON.parseObject(memberJson,Map.class);

		return user_map;

	}


	/**
	 * 将微博的 未知-n 男-m 女-f  转化成 0 1 2
	 * @param gender
	 * @return
	 */
	public static Integer formatGender(String gender){
		Integer sex = UmsConst.USER_SEX_UNKNOWN;
		if("m".equals(gender)){
			sex = UmsConst.USER_SEX_MAN;
		}else if("f".equals(gender)){
			sex = UmsConst.USER_SEX_WOMAN;
		}
		return sex;
	}




	public static void main(String[] args) {

		//2.00pjCAxGRbx1uBe4236e7ed9kVd_EB

		//System.out.println(getAccessToken());


		//getMemberInfo();
	}




}
