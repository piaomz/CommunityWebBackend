package me.piaomz.piaomz.mapper;

import me.piaomz.piaomz.pojo.Subscribe;
import me.piaomz.piaomz.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface UserMapper{
    List<User> selectUser();
    User queryUser(int uid);
    User loginUser(String username);

    int signupUser(@Param("user") User user, @Param("username") String username, @Param("password") String password);

    int deleteUser(int uid);

    int updateUserInfo(String nickname,String avatarUrl ,String phone ,int uid );

    int updateUserAdmin(int uid);
    int updateUserTest(int uid);

    void loginHisInsert(int uid, Date time);
    void clearLoginHis(int uid);

    List<Subscribe> selectSubscribe(int uid);
    List<Subscribe> selectSubscriber(int uid);
    void newSubscribe(Subscribe subscribe);
    void deleteSubscribe(int uid,int subscribeUid);

    List<User> searchUserByNickName(String nickname);
}
