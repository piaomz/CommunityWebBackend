package me.piaomz.piaomz.service;

import me.piaomz.piaomz.pojo.User;
import me.piaomz.piaomz.utils.ResponseResult;

public interface UserService {
    ResponseResult login(User user);

    ResponseResult logout();
    ResponseResult signup(User user);
    ResponseResult deleteUser(User user);

    ResponseResult updateUserInfo(String nickname,String avatarUrl,String phone,String uid,String type);

    ResponseResult queryUser(String uid);

    ResponseResult clearHistory(String uid);

    ResponseResult newSubscribe(String subscribeuid);

    ResponseResult selectSubscribe(String uid);

    ResponseResult selectSubscriber(String uid);

    ResponseResult deleteSubscribe(String subscribeUid);

    ResponseResult searchUserByNickName(String nickname);
}
