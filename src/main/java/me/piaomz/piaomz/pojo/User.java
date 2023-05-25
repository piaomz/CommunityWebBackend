package me.piaomz.piaomz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    private int uid;
    private String username;
    private String password;
    private String nickname;
    private String avatarurl;
    private String phone;
    private String type;

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", pwd='" + password + '\'' +
                '}';
    }


}
