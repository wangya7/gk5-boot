package wang.bannong.gk5.boot.sample.dubbo;

import jakarta.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.bannong.gk5.boot.sample.dubbo.entity.HospitalPatient;
import wang.bannong.gk5.boot.sample.dubbo.facade.StudentDTO;
import wang.bannong.gk5.boot.sample.dubbo.facade.StudentServiceFacade;
import wang.bannong.gk5.boot.sample.dubbo.mapper.HospitalPatientMapper;

@Slf4j
@Service
public class HospitalPatientServiceImpl implements HospitalPatientService {
    @Resource
    private HospitalPatientMapper hospitalPatientMapper;
    @DubboReference
    private StudentServiceFacade studentServiceFacade;


    @Override
    public String hello(String greet) {
        return studentServiceFacade.hello(greet);
    }

    @Override
    public StudentDTO queryStudentByName(String studentName) {
        StudentDTO query = new StudentDTO();
        query.setName(studentName);
        log.info("query student by name[{}]", studentName);
        return studentServiceFacade.queryByName(query);
    }

    @Override
    public HospitalPatient selectById(Long id) {
        return hospitalPatientMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean txTest(String mobile, String name) {
        log.info("mobile={}, name={}", mobile, name);
        HospitalPatient patient = hospitalPatientMapper.selectByMobile(mobile);
        Objects.requireNonNull(patient, "patient cannot be found.");
        patient.setName(name);
        patient.setUpdateTime(new Date());
        if (hospitalPatientMapper.updateByPrimaryKeySelective(patient) > 0) {
            String outPatientId = patient.getOutPatientId();
            if (StringUtils.isNumeric(outPatientId)) {
                boolean callback = studentServiceFacade.incrSize(Long.valueOf(outPatientId));
                log.info("RPC,outPatientId={}, callback={}", outPatientId, callback);
                return callback;
            }
        }
        return false;
    }
}
