package jsu.per.system.DTO;

import jsu.per.system.pojo.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO extends Admin {
    private String token;
    private String rolename;
}
