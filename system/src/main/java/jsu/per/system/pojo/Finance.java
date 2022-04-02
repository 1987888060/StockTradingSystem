package jsu.per.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Finance {
    private String stockname;
    private String stockcode;
    private int buyed;
    private int sell;
    private int have;
    //该持有的股票价值
    private double value;
    //收益
    private double earnings;

}
