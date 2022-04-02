package jsu.per.system.shiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.UsernamePasswordToken;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken extends UsernamePasswordToken {
    private String token;
    private int type;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
