package wang.bannong.gk5.boot.sample.dubbo;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wang.bannong.gk5.boot.sample.dubbo.entity.Student;
import wang.bannong.gk5.boot.sample.dubbo.mapper.StudentMapper;

@RestController
@RequestMapping
public class StudentController {

    @Resource
    private StudentMapper studentMapper;

    @GetMapping("query")
    public String queryById(@RequestParam("id") Integer id) {
        Student student = studentMapper.selectByPrimaryKey(id);
        return student != null ? String.valueOf(student) : "unfound student";
    }
}
