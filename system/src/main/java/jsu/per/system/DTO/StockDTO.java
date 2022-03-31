package jsu.per.system.DTO;

import jsu.per.system.pojo.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO extends Stock {
    private int num;
}
