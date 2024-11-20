package wang.bannong.gk5.boot.sample.dubbo;

import java.util.Objects;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;
import wang.bannong.gk5.boot.sample.dubbo.entity.Student;
import wang.bannong.gk5.boot.sample.dubbo.facade.StudentDTO;
import wang.bannong.gk5.boot.sample.dubbo.facade.StudentServiceFacade;
import wang.bannong.gk5.boot.sample.dubbo.mapper.StudentMapper;

@Slf4j
@DubboService
public class StudentServiceImpl implements StudentServiceFacade {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public String hello(String message) {
        Objects.requireNonNull(message, "Sorry, message cannot be null!");
        return "... " + message;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean incrSize(Long studentId) {
        log.info("studentId={}", studentId);
        Student student = studentMapper.selectByPrimaryKey(studentId);
        Objects.requireNonNull(student, "cannot find student");
        student.setSize(student.getSize() + 1);
        boolean result = studentMapper.updateByPrimaryKey(student) > 0;
        // 增加模拟
        if (student.getSize() > 0) {
            throw new BizException("Manual Set");
        }
        return result;
    }

    @Override
    public StudentDTO queryByName(StudentDTO studentDTO) {
        StudentDTO dto = new StudentDTO();
        dto.setName("NA");
        dto.setAge(18);
        dto.setScore(100l);
        dto.setNew(false);
        return dto;
    }
}
