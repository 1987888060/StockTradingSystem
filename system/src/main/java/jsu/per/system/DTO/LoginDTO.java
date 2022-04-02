package jsu.per.system.DTO;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登陆信息
 */
@Data
public class LoginDTO {

    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    //0:管理员 1:用户
    private int type;

}
