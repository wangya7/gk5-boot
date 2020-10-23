package wang.bannong.gk5.boot.sample.mybatis1ms.mapper;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import wang.bannong.gk5.boot.sample.mybatis1ms.domain.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectByIds(List<Long> ids);

    User selectByMobile(@Param("mobile") String mobile);
    User selectByOpenid(@Param("openid") String openid);

    Page<User> selectListByNameLike(String name);
}
