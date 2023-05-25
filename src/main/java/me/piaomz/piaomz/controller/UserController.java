package me.piaomz.piaomz.controller;

import me.piaomz.piaomz.mapper.UserMapper;
import me.piaomz.piaomz.pojo.User;

import me.piaomz.piaomz.service.UserService;
import me.piaomz.piaomz.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @PostMapping("/user/login")
    // Require  Username and Password
    public ResponseResult login(@RequestBody User user){
        return userService.login(user);
    }
    @RequestMapping("/user/logout")
    // Require Token
    public ResponseResult logout(){
        return userService.logout();
    }

    @PostMapping("/user/signup")
    // Require  Username and Password
    public ResponseResult signup(@RequestBody User user){
        return userService.signup(user);
    }
    @PostMapping("/user/deleteUser")
    // Require Token,  Username and Password
    public ResponseResult deleteUser(@RequestBody User user){
        userService.logout();
        return userService.deleteUser(user);
    }
    @GetMapping("user/update")
    public ResponseResult updateUserInfo(@RequestParam(value = "nickname", defaultValue = "") String nickname,
                                         @RequestParam(value = "avatarUrl", defaultValue = "") String avatarUrl,
                                         @RequestParam(value = "phone", defaultValue = "")     String phone ,
                                         @RequestParam(value = "uid", defaultValue = "")       String uid  ,
                                         @RequestParam(value = "type", defaultValue = "")       String type  ){
        return userService.updateUserInfo(nickname,avatarUrl,phone,uid,type);
    }
    @GetMapping("user/query")
    public ResponseResult updateUserInfo(@RequestParam(value = "uid", defaultValue = "")       String uid  ){
        return userService.queryUser(uid);
    }

    @GetMapping("user/search")
    public ResponseResult search(@RequestParam(value = "nickname", defaultValue = "")       String nickname  ){
        return userService.searchUserByNickName(nickname);
    }
    @GetMapping("user/clearHis")
    public ResponseResult clearHistory(@RequestParam(value = "uid", defaultValue = "")       String uid){
        return userService.clearHistory(uid);
    }

    @GetMapping("user/newSubscribe")
    public ResponseResult newSubscribe(@RequestParam(value = "uid", defaultValue = "")       String uid){
        return userService.newSubscribe(uid);
    }

    @GetMapping("user/selectSubscribe")
    public ResponseResult selectSubscribe(@RequestParam(value = "uid", defaultValue = "")       String uid){
        return userService.selectSubscribe(uid);
    }

    @GetMapping("user/selectSubscriber")
    public ResponseResult selectSubscriber(@RequestParam(value = "uid", defaultValue = "")       String uid){
        return userService.selectSubscriber(uid);
    }

    @GetMapping("user/deleteSubscribe")
    public ResponseResult deleteSubscribe(@RequestParam(value = "uid", defaultValue = "")       String uid){
        return userService.deleteSubscribe(uid);
    }
}
