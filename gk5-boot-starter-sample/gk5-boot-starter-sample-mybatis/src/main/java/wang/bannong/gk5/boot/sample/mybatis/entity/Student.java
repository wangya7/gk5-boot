package wang.bannong.gk5.boot.sample.mybatis.entity;

import java.io.Serializable;


import lombok.Data;

@Data
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String  name;
    private Integer age;
    private String  num;
    private Byte    type;
}