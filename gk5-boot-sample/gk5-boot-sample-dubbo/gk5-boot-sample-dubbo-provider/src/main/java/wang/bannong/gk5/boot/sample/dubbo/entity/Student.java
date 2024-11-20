package wang.bannong.gk5.boot.sample.dubbo.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

/**
 * 表名：student
*/
@Data
@Table(name = "`student`")
public class Student implements Serializable {
    @Id
    @Column(name = "`id`")
    private Long id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`age`")
    private Integer age;

    @Column(name = "`num`")
    private String num;

    @Column(name = "`type`")
    private Byte type;

    @Column(name = "`size`")
    private Long size;

    private static final long serialVersionUID = 1L;
}