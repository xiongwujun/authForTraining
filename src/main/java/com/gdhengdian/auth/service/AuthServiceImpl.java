package com.gdhengdian.auth.service;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdhengdian.auth.common.AuthCodeConstant;
import com.gdhengdian.auth.common.AuthMessage;
import com.gdhengdian.auth.common.HttpStateEnum;
import com.gdhengdian.auth.common.RoleConstant;
import com.gdhengdian.auth.manager.AuthManager;
import com.gdhengdian.auth.manager.TokenManager;
import com.gdhengdian.auth.mapper.TokenMapper;
import com.gdhengdian.auth.model.Token;

/**
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-10
 */
@Service
public class AuthServiceImpl implements AuthService {

	private final static Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

	private final AuthManager authManager;
	private final TokenManager tokenManager;
	private final TokenMapper mapper;


	@Autowired
	public AuthServiceImpl(AuthManager authManager, TokenManager tokenManager,TokenMapper mapper) {
		this.authManager = authManager;
		this.tokenManager = tokenManager;
		this.mapper = mapper;
	}



	@Override
	public AuthMessage login(String username, String password) {
		// 1. 校验输入参数是否为空
		boolean hasNull = this.hasNull(username, password);
		if (hasNull) {
			return AuthMessage.badRequest();
		}
		// 2. 验证是否正确并讲结果放入缓存，这是第一次登陆，还没有token，也有可能没有退出，此时还是有token的
		Token userTypeAndId = tokenManager.getTokenByUsernameAndPassword(username, password);
		if (userTypeAndId == null) {
			return new AuthMessage(HttpStateEnum.USERNAME_OR_PASSWORD_ERROR, null);
		}
		//有
		if(userTypeAndId.getAccessTokenId()!=null) {
			tokenManager.removeAccessTokenIdByTokenId(userTypeAndId, userTypeAndId.getAccessTokenId());
		}
		// 4. 创建新的访问令牌并放入缓存
		String accessTokenId = authManager.createAccessTokenId();
		String accessToken = authManager.createAccessTokenByAccessTokenId(accessTokenId);
		//调用缓存层去更新缓存
		userTypeAndId.setAccessTokenId(accessTokenId);
		tokenManager.saveAccessTokenIdByTokenId(userTypeAndId);
		int success = mapper.updateAccessTokenIdByTokenId(userTypeAndId);
		if (success == 0) {
			return new AuthMessage(HttpStateEnum.UNKNOWN_EXCEPTION, null);
		}
		LOGGER.info("id为{}类型为{}的用户登录成功", userTypeAndId.getId(), userTypeAndId.getType());
		// 5. 返回用户令牌，和成功状态码
		AuthMessage authMessage=new AuthMessage();
		authMessage.setStatus(200);
		authMessage.setAccessToken(accessToken);
		ObjectMapper om=new ObjectMapper();
		String jsonObject;
		try {
			jsonObject = om.writeValueAsString(userTypeAndId);
		} catch (JsonProcessingException e) {
			return AuthMessage.badRequest();
		}
		//把用户类型也返回过去
		authMessage.setMessage(jsonObject);
		return authMessage;
	}

	@Override
	public AuthMessage logout(String accessToken) {
		// 1. 校验输入参数是否为空
		boolean hasNull = this.hasNull(accessToken);
		if (hasNull) {
			return AuthMessage.badRequest();
		}
		// 2. 验证访问令牌并获得访问令牌id,访问令牌ID有可能获取不到
		String accessTokenId = authManager.getAccessTokenIdByAccessToken(accessToken);
		if(accessTokenId==null)	{
			return AuthMessage.badRequest();
		}

		// 3. 从缓存中获取用户令牌ID，有可能还没存
		Token token = tokenManager.getTokenByAccessTokenId(accessTokenId);
		if(token==null) {
			return AuthMessage.badRequest();
		}
		// 4. 从缓存中删除数据
		int i = tokenManager.removeAccessTokenIdByTokenId(token, accessTokenId);
		if (i > 0) {
			LOGGER.info("id为{}的用户登出成功", token.getId());
		}
		return AuthMessage.success(null);
	}
	/**
	 * 判断是否有权限执行服务
	 */
	@Override
	public AuthMessage accredit(String accessToken, Integer authCode) {
		// 1. 校验输入参数是否为空
		boolean hasNull = this.hasNull(accessToken, authCode);
		if (hasNull) {
			return AuthMessage.badRequest();
		}
		// 2. 判断权限
		Boolean hasAuth = this.hasAuthToDoSomeThing(accessToken, authCode);
		//有权限
		if (hasAuth) {
			return AuthMessage.success(null);
		} else {
			return new AuthMessage(HttpStateEnum.UNAUTHORIZED, null);
		}
	}
	//不一定是重置自己的密码，可以改密码
	@Override
	public AuthMessage resetPassword(String accessToken, String username, String password) {
		// 1. 校验输入参数是否为空
		boolean hasNull = this.hasNull(accessToken, username, password);
		if (hasNull) {
			return AuthMessage.badRequest();
		}
		// 2. 判断用户是否有权限并是否执行重置密码
		// 2.1 获取操作执行者的用户类型、id
		//如果这里找不到会报错 这里accessTokenId可能为空
		String accessTokenId = authManager.getAccessTokenIdByAccessToken(accessToken);

		if (accessTokenId == null) {
			return AuthMessage.badRequest();
		}
		//找到了要修改密码的用户，token虽然是合法的，但是可能这个token没有绑定用户，所以这里有可能找不到
		Token userTypeAndId = tokenManager.getTokenByAccessTokenId(accessTokenId);
		if(userTypeAndId==null) {
			return AuthMessage.badRequest();
		}
		//再去判断原用户密码是否正确
		// 2.2 获取被执行者的用户类型、id
		Token targetUserType = tokenManager.getTokenByUsernameAndPassword(username, password);
		if (targetUserType == null) {
			return new AuthMessage(HttpStateEnum.USERNAME_OR_PASSWORD_ERROR, null);
		}
		// 2.3 判断能否执行
		int run = 0;
		//如果是管理员
		if (RoleConstant.ADMIN == userTypeAndId.getType()) {
			run = 1;
		} else {
			//都是同一类型
			if (userTypeAndId.getId().intValue() == targetUserType.getId().intValue()) {
				run = 2;
			}
		}
		// 权限判断完毕,更新密码
		if (run == 0) {
			return new AuthMessage(HttpStateEnum.UNAUTHORIZED, null);
		}
		Integer success = tokenManager.updatePassword(username, password);
		if (success == 0) {
			return new AuthMessage(HttpStateEnum.UNKNOWN_EXCEPTION, null);
		}
		LOGGER.info("id为{}的用户给id为{}的用户重置密码成功", userTypeAndId.getId(), targetUserType.getId());

		//如果这个账号里面已经有token值，删除缓存，并且将这个数据表中的token设置为null
		if (targetUserType.getAccessTokenId() != null) {
			int i = tokenManager.removeAccessTokenIdByTokenId(targetUserType, targetUserType.getAccessTokenId());
			if (i > 0) {
				LOGGER.info("id为{}的用户已被强制下线", targetUserType.getId());
			}
		}
		return AuthMessage.success(null);
	}


	@Override
	public AuthMessage saveUserTokens(String accessToken, List<Token> tokenVoList) {
		// 1. 校验输入参数是否为空
		boolean hasNull = this.hasNull(accessToken, tokenVoList);
		if (hasNull || tokenVoList.isEmpty()) {
			return AuthMessage.badRequest();
		}
		//保存用户的权限代码
		Integer authCode = AuthCodeConstant.SAVE_USER_TOKEN;
		//判断有没有权限做某事就已经判断了此用户是否合法
		Boolean hasAuth = this.hasAuthToDoSomeThing(accessToken, authCode);
		if (hasAuth) {
			Boolean flag = tokenManager.batchSaveTokens(tokenVoList);
			if(flag) {
				LOGGER.info("添加了{}个用户", tokenVoList.size());
				return AuthMessage.success(null);
			}else {
				return new AuthMessage(HttpStateEnum.UNKNOWN_EXCEPTION, null);
			}
		} else {
			return new AuthMessage(HttpStateEnum.UNAUTHORIZED, null);
		}
	}

	/**
	 * 判断是否有权限
	 * @param accessToken
	 * @param authCode
	 * @return
	 */
	private Boolean hasAuthToDoSomeThing(String accessToken, Integer authCode) {
		String accessTokenId = authManager.getAccessTokenIdByAccessToken(accessToken);
		if(accessTokenId==null) {
			return false;
		}
		//通过tokenID拿到了Token实体类
		//TokenId
		Token userTypeAndId = tokenManager.getTokenByAccessTokenId(accessTokenId);
		if (userTypeAndId == null) {
			return false;
		}
		LOGGER.info("id为{}想要执行代号为{}的操作", userTypeAndId.getId(), authCode);
		return authManager.hasAuthToDoSomeThing(userTypeAndId.getType(), authCode);
	}

	/**
	 * 判断是否有空值
	 *
	 * @param args 参数列表
	 * @return 是否有空值
	 */
	private boolean hasNull(Object... args) {
		for (Object obj : args) {
			if (Objects.isNull(obj)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 删除用户
	 */
	@Override
	public AuthMessage deleteUser(String userId) {
		if(userId==null || "".equals(userId)) {
			return AuthMessage.badRequest();
		}
		Token tokenByUserId = mapper.getTokenByUserId(userId);
		if(tokenByUserId==null) {
			//说明此ID用户没有在授权中心注册，不需要删除
			return AuthMessage.success(null); 
		}
		if(tokenByUserId.getAccessTokenId()!=null) {
			tokenManager.deleteAccessTokenIdCache(tokenByUserId.getAccessTokenId());
		}
		int result = mapper.deleteUserTokenById(userId);
		if(result==1) {
			return AuthMessage.success(null);
		}
		return AuthMessage.badRequest();
	}
}
