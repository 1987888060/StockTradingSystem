package jsu.per.system.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    //账号
    private String username;
    //密码
    private String password;
    //邮箱
    private String email;
    //验证码
    private String vcode;
}
