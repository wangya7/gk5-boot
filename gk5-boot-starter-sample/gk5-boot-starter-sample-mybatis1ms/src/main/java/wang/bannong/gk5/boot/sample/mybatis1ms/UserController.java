package wang.bannong.gk5.boot.sample.mybatis1ms;

import com.github.pagehelper.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wang.bannong.gk5.boot.sample.mybatis1ms.domain.User;

@RestController
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserDAO userDao;

    @RequestMapping(value = "/user/m/queryById", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User queryById1(Long id) {
        log.info("Query master user by id[{}]", id);
        return userDao.selectByPrimaryKey(id);
    }

    @RequestMapping(value = "/user/s/queryById", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<User> queryById2(String name) {
        log.info("Query slave user by name[{}]", name);
        return userDao.selectListByNameLike(name);
    }
}
