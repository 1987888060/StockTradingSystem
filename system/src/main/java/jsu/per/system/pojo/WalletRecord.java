package jsu.per.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("walletrecord")
public class WalletRecord {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //用户id
    private int userid;
    private Date time;
    private double money;
    private int type;
}
