package wang.bannong.gk5.boot.sample.mybatis;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;


import net.minidev.json.JSONArray;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.bannong.gk5.boot.sample.mybatis.entity.Student;
import wang.bannong.gk5.boot.sample.mybatis.mapper.StudentMapper;
import wang.bannong.gk5.boot.starter.web.security.annotation.Anonymous;

@RestController
@RequestMapping("/mybatis")
public class MybatisController {

    private final static Logger log = LoggerFactory.getLogger(MybatisController.class);

    @Resource
    private StudentMapper studentMapper;

    @Anonymous
    @GetMapping("/test")
    public String test() {
        // Student student = new Student();
        // student.setId(11);
        // student.setName("柚子");
        // student.setAge(32);
        // student.setNum("32");
        // student.setType((byte)0);
        // studentMapper.insert(student);

        List<Student> s = studentMapper.selectByIds(Arrays.asList(11, 32));
        return CollectionUtils.isNotEmpty(s) ? JSONArray.toJSONString(s) : "";
    }
}
