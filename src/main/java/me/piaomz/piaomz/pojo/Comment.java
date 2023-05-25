package me.piaomz.piaomz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int commentid;
    private int articleid;
    private int uid;
    private String content;
    private Date time;

    public Comment(int articleid, int uid, String content, Date time) {
        this.articleid=articleid;
        this.uid=uid;
        this.content=content;
        this.time =time;
    }
}
