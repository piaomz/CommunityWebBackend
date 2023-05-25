package me.piaomz.piaomz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryArticle {
    private int hisId;
    private int uid;
    private int articleid;
    private Date time;
}
