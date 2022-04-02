package jsu.per.system.DTO;

import jsu.per.system.pojo.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO extends Wallet {
    private String username;

}
