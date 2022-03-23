package jsu.per.system.pojo;

import lombok.Data;

@Data
public class UserToken {
    //注意 int ----> String
    private String user_id;
    private String token;
}
