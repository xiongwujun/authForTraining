package com.gdhengdian.auth.mapper;

import com.gdhengdian.auth.model.Token;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-11
 */
@Mapper
@Repository
public interface TokenMapper {

	/**
	 * 根据用户账号修改密码
	 *
	 * @param username 账号
	 * @param password 密码
	 * @return 受影响的行数
	 */
	@Update({
		"UPDATE token SET password = #{password} WHERE username = #{username}"
	})
	int updatePassword(@Param("username") String username, @Param("password") String password);

	/**
	 * 根据账号和密码查找令牌
	 *
	 * @param username 账号
	 * @param password 密码
	 * @return 令牌
	 */
	@Select({
		"SELECT id, type FROM token WHERE username = #{username} AND password = #{password}"
	})
	Token getTokenByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

	/**
	 * 批量插入令牌
	 *
	 * @param tokenList 令牌列表
	 * @return 受影响的行数
	 */
	@Insert({
		"<script>",
		"INSERT INTO token(username, password, type) VALUES ",
		"<foreach collection='tokenList' item='item' index='index' separator=','>" +
				"(#{item.username}, #{item.password},#{item.type})",
				"</foreach>",
				"</script>"
	})
	int batchSaveUserToken(@Param("tokenList") List<Token> tokenList);

	/**
	 * 根据访问令牌查找用户令牌
	 *
	 * @param accessTokenId 访问令牌
	 * @return 用户令牌
	 */
	@Select({
		"SELECT id, type FROM token WHERE access_token_id = #{accessTokenId}"
	})
	Token getUserTokenByAccessTokenId(String accessTokenId);

	/**
	 * 根据id更新用户的访问令牌ID
	 *
	 * @param token 用户令牌Id和访问令牌ID
	 * @return 受影响的行数
	 */
	@Update({
		"UPDATE token SET access_token_id = #{accessTokenId,jdbcType=NULL} WHERE id = #{id}"
	})
	int updateAccessTokenIdByTokenId(Token token);

	/**
	 * 根据账号获取用户的id和用户类型
	 *
	 * @param username 用户账号
	 * @return id和用户类型
	 */
	@Select({
		"SELECT id,type FROM token WHERE username = #{username}"
	})
	Token getUserTokenByUsername(String username);
	/**
	 * 根据ID删除用户
	 * @param userId
	 * @return
	 */
	@Delete({
		"DELETE FROM token WHERE id = #{userId}"
	})
	int deleteUserTokenById(String userId);
	/**
	 * 根据ID查找用户的访问令牌
	 * @param userId 用户ID
	 * @return
	 */
	@Select({
		"SELECT id, access_token_id FROM token WHERE id = #{userId}"
	})
	Token getTokenByUserId(String userId);
}
