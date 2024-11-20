package wang.bannong.gk5.boot.sample.dubbo;

import wang.bannong.gk5.boot.sample.dubbo.entity.HospitalPatient;
import wang.bannong.gk5.boot.sample.dubbo.facade.StudentDTO;

public interface HospitalPatientService {

    String hello(String greet);

    StudentDTO queryStudentByName(String studentName);

    HospitalPatient selectById(Long id);

    boolean txTest(String mobile, String name);

}
