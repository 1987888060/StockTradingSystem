package jsu.per.system.DTO;

import jsu.per.system.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends User {
    private String token;
    private String rolename;
    //用于修改邮箱和密码时使用
    private String vcode;
}
