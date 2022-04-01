package jsu.per.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("buyedstock")
public class BuyedStock {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private int userid;
    private String code;
    private int num;
}
