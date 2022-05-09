package wang.bannong.gk5.boot.sample.web.dto;

import java.io.Serializable;


import lombok.Data;

@Data
public class LoginDto implements Serializable {
    private static final long serialVersionUID = 4733425767002388986L;

    private String mobile;
    private String password;
}
