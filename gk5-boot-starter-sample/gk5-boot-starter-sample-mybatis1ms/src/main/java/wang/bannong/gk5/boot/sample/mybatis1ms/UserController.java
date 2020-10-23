package wang.bannong.gk5.boot.sample.mybatis1ms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wang.bannong.gk5.boot.sample.mybatis1ms.domain.User;
import wang.bannong.gk5.boot.sample.mybatis1ms.mapper.UserMapper;

@RestController
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper masterUserMapper;

    @Autowired
    private UserMapper slaveUserMapper;

    @RequestMapping(value = "/user/m/queryById", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User queryById1(Long id) {
        log.info("Query master user by id[{}]", id);
        return masterUserMapper.selectByPrimaryKey(id);
    }

    @RequestMapping(value = "/user/s/queryById", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User queryById2(Long id) {
        log.info("Query slave user by id[{}]", id);
        return slaveUserMapper.selectByPrimaryKey(id);
    }
}
