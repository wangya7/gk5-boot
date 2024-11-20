package wang.bannong.gk5.boot.sample.dubbo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 * 表名：hospital_patient
 * 表注释：患者信息
*/
@Data
@Table(name = "`hospital_patient`")
public class HospitalPatient implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    private Long id;

    /**
     * 医院标识
     */
    @Column(name = "`hospital_id`")
    private Long hospitalId;

    /**
     * 外部患者标识
     */
    @Column(name = "`out_patient_id`")
    private String outPatientId;

    /**
     * 姓名
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 身份证号码,HIS同步会为空
     */
    @Column(name = "`identity_card_number`")
    private String identityCardNumber;

    /**
     * 手机号码
     */
    @Column(name = "`mobile`")
    private String mobile;

    /**
     * 
户籍地址
     */
    @Column(name = "`permanent_residence_address`")
    private String permanentResidenceAddress;

    /**
     * 居住地址
     */
    @Column(name = "`residential_address`")
    private String residentialAddress;

    /**
     * 婚姻状况
     */
    @Column(name = "`marital_status`")
    private Boolean maritalStatus;

    /**
     * 医保类型
     */
    @Column(name = "`medical_insurance`")
    private Boolean medicalInsurance;

    /**
     * 医保卡号
     */
    @Column(name = "`medical_insurance_number`")
    private String medicalInsuranceNumber;

    /**
     * 健康卡号
     */
    @Column(name = "`health_number`")
    private String healthNumber;

    /**
     * 备注
     */
    @Column(name = "`remarks`")
    private String remarks;

    /**
     * 删除状态；0-未删除 1-已删除
     */
    @Column(name = "`is_del`")
    private Byte isDel;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "`create_id`")
    private Long createId;

    /**
     * 更新时间
     */
    @Column(name = "`update_time`")
    private Date updateTime;

    /**
     * 更新人
     */
    @Column(name = "`update_id`")
    private Long updateId;

    private static final long serialVersionUID = 1L;
}