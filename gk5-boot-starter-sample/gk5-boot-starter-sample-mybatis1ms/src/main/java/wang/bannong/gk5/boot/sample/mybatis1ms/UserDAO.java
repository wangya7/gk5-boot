package wang.bannong.gk5.boot.sample.mybatis1ms;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import wang.bannong.gk5.boot.sample.mybatis1ms.domain.User;
import wang.bannong.gk5.boot.sample.mybatis1ms.mapper.UserMapper;

@Repository
public class UserDAO {
    @Autowired
    private UserMapper masterUserMapper;
    @Autowired
    private UserMapper slaveUserMapper;

    public int deleteByPrimaryKey(Long id) {
        return masterUserMapper.deleteByPrimaryKey(id);
    }

    public int insert(User record) {
        return masterUserMapper.insert(record);
    }

    public int insertSelective(User record) {
        return masterUserMapper.insertSelective(record);
    }

    public User selectByPrimaryKey(Long id) {
        return slaveUserMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(User record) {
        return masterUserMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(User record) {
        return masterUserMapper.updateByPrimaryKey(record);
    }

    public List<User> selectByIds(List<Long> ids) {
        return slaveUserMapper.selectByIds(ids);
    }

    public User selectByMobile(String mobile) {
        return slaveUserMapper.selectByMobile(mobile);
    }

    public User selectByOpenid(String openid) {
        return slaveUserMapper.selectByOpenid(openid);
    }

    public Page<User> selectListByNameLike(String name) {
        PageHelper.startPage(1, 5, " modify_time desc ");
        return slaveUserMapper.selectListByNameLike(name);
    }
}
