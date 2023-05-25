package me.piaomz.piaomz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    private int articleid;
    private int uid;
    private Date lastmod;
    private Date createtime;
    private String title;
    private String content;
    private String type;
    private String imageurl;
}
