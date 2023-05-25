package me.piaomz.piaomz.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscribe {
    private int uid;
    private int subscribeUid;
    private Date time;
}
