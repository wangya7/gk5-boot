package wang.bannong.gk5.boot.sample.dubbo;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wang.bannong.gk5.boot.sample.dubbo.entity.HospitalPatient;
import wang.bannong.gk5.boot.sample.dubbo.facade.StudentDTO;

@Slf4j
@RestController
@RequestMapping
public class HospitalPatientController {

    @Resource
    private HospitalPatientService hospitalPatientService;

    @GetMapping("query")
    public String queryById(@RequestParam("id") Long id) {
        HospitalPatient patient = hospitalPatientService.selectById(id);
        return patient != null ? String.valueOf(patient) : "unfound patient";
    }

    @GetMapping("tx")
    public boolean trxTest(@RequestParam("mobile") String mobile, @RequestParam("name") String name) {
        return  hospitalPatientService.txTest(mobile, name);
    }

    @GetMapping("hello")
    public String hello(@RequestParam("greet") String greet) {
        return hospitalPatientService.hello(greet);
    }

    @GetMapping("queryName")
    public String queryStudentByName(@RequestParam("studentName") String studentName) {
        StudentDTO dto = hospitalPatientService.queryStudentByName(studentName);
        return dto != null ? dto.getName() : "Not Found";
    }
}
