package wang.bannong.gk5.boot.sample.dubbo.mapper;

import tk.mybatis.mapper.common.Mapper;
import wang.bannong.gk5.boot.sample.dubbo.entity.HospitalPatient;

public interface HospitalPatientMapper extends Mapper<HospitalPatient> {

    HospitalPatient selectByMobile(String mobile);

}