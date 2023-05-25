package me.piaomz.piaomz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentFavorite {
    private int commentid;
    private int uid;
    private Date time;
}
