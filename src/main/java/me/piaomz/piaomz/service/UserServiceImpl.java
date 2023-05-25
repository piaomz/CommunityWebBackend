package me.piaomz.piaomz.service;

import me.piaomz.piaomz.mapper.UserMapper;
import me.piaomz.piaomz.pojo.Subscribe;
import me.piaomz.piaomz.pojo.User;
import me.piaomz.piaomz.utils.JwtUtil;
import me.piaomz.piaomz.utils.RedisCache;
import me.piaomz.piaomz.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper;
    @Override
    public ResponseResult login(User user) {
        //authenticationManager
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //error return error msg
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("Wrong Password or Username");
        }

        //pass return jwt
        LoginUserDetails loginUserDetails = (LoginUserDetails) authenticate.getPrincipal();
        String uid =Integer.toString(loginUserDetails.getUser().getUid());
        String jwt = JwtUtil.createJWT(uid);
        HashMap<String,String> data = new HashMap<>();
        data.put("token", jwt);
        data.put("uid",uid);
        //redis store jwt, userid as key
        redisCache.setCacheObject("login:"+uid,loginUserDetails);
        userMapper.loginHisInsert(Integer.parseInt(uid),new Date());
        return new ResponseResult(200,"Login Successful",data);
    }

    @Override
    public ResponseResult logout() {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int uid = loginUserDetails.getUser().getUid();
        redisCache.deleteObject("login:"+uid);
        return new ResponseResult(200,"Logout Successful");
    }

    @Override
    public ResponseResult signup(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        try{
            userMapper.signupUser(user,user.getUsername(),bCryptPasswordEncoder.encode(user.getPassword()));
        }catch (Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "Signup error, Existed username!");
        }
        return new ResponseResult(200,"Signup Successful, uid:"+user.getUid());
    }

    @Override
    public ResponseResult deleteUser(User user) {
        //authenticationManager
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //error return error msg
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("Wrong Password or Username");
        }

        LoginUserDetails loginUserDetails = (LoginUserDetails) authenticate.getPrincipal();
        int uid =loginUserDetails.getUser().getUid();
        userMapper.deleteUser(uid);
        return new ResponseResult(200,"Delete User Successful");
    }

    @Override
    public ResponseResult updateUserInfo(String nickname, String avatarUrl, String phone, String uid, String type) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int tokenuid = loginUserDetails.getUser().getUid();
        // uid if is itself
        ResponseResult result =null;
        try{
            Integer.parseInt(uid);
        }catch (Exception e){
            result = new ResponseResult<>(HttpStatus.FORBIDDEN.value(),"uid illegal");
        }
        if(!StringUtils.hasText(uid)){
            result = new ResponseResult(HttpStatus.FORBIDDEN.value(), "Need uid Params");
        }else if(!Integer.toString(tokenuid).equals(uid) && !loginUserDetails.getUser().getType().equals("admin")){
            result = new ResponseResult(HttpStatus.FORBIDDEN.value(), "Not permit to update with this user");
        }else if(StringUtils.hasText(type) && !loginUserDetails.getUser().getType().equals("admin")){
            result = new ResponseResult(HttpStatus.FORBIDDEN.value(), "Not permit to update type with this user");
        }else{
            if(type.equals("admin")){
                userMapper.updateUserAdmin(Integer.parseInt(uid));
            }else if(type.equals("test")){
                userMapper.updateUserTest(Integer.parseInt(uid));
            }else if(!type.equals("")){
                result = new ResponseResult(HttpStatus.FORBIDDEN.value(), "type illegal");
                return result;
            }
            userMapper.updateUserInfo(nickname,avatarUrl,phone,Integer.parseInt(uid));
            result = new ResponseResult(200,"Update successful");
        }
        return result;
    }

    @Override
    public ResponseResult queryUser(String uid) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int tokenuid = loginUserDetails.getUser().getUid();
        try{
            User user = userMapper.queryUser(Integer.parseInt(uid));
            if(Objects.isNull(user)){
                return new ResponseResult(HttpStatus.FORBIDDEN.value(), "uid illegal");
            }
            if(!loginUserDetails.getUser().getType().equals("admin")){
                user.setPassword(null);
            }
            HashMap<String,Object> data = new HashMap<>();
            data.put("user",user);
            return new ResponseResult(200,data);
        }
        catch (Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "uid illegal");
        }
    }

    @Override
    public ResponseResult clearHistory(String uid) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int tokenuid = loginUserDetails.getUser().getUid();
        int parseuid;
        try{
            parseuid = Integer.parseInt(uid);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "uid illegal");
        }
        if(!loginUserDetails.getUser().getType().equals("admin") && tokenuid!=parseuid){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "not permitted to clear others His");
        }
        userMapper.clearLoginHis(parseuid);
        return new ResponseResult<>(HttpStatus.OK.value(), "Clear Successful");
    }

    @Override
    public ResponseResult newSubscribe(String subscribeuid) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int tokenuid = loginUserDetails.getUser().getUid();

        int parsesubscribeuid;
        try{
            parsesubscribeuid = Integer.parseInt(subscribeuid);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "uid illegal");
        }
        try{
            Subscribe subscribe = new Subscribe(tokenuid,parsesubscribeuid,new Date());
            userMapper.newSubscribe(subscribe);
            return new ResponseResult<>(HttpStatus.OK.value(), "Subscribe Successful");
        }catch(Exception e){
            //System.out.println(e.getMessage());
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "already subscribe or not found uid");
        }


    }

    @Override
    public ResponseResult selectSubscribe(String uid) {
        int parseuid;
        try{
            parseuid = Integer.parseInt(uid);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "uid illegal");
        }
        List<Subscribe> subscribeList=userMapper.selectSubscribe(parseuid);
        return new ResponseResult<>(HttpStatus.OK.value(), subscribeList);
    }

    @Override
    public ResponseResult selectSubscriber(String uid) {
        int parseuid;
        try{
            parseuid = Integer.parseInt(uid);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "uid illegal");
        }
        List<Subscribe> subscribeList=userMapper.selectSubscriber(parseuid);
        return new ResponseResult<>(HttpStatus.OK.value(), subscribeList);
    }

    @Override
    public ResponseResult deleteSubscribe(String subscribeUid) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int tokenuid = loginUserDetails.getUser().getUid();

        int parsesubscribeuid;
        try{
            parsesubscribeuid = Integer.parseInt(subscribeUid);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "uid illegal");
        }
        userMapper.deleteSubscribe(tokenuid,parsesubscribeuid);
        return new ResponseResult<>(HttpStatus.OK.value(), "Delete Subscribe Successful");
    }

    @Override
    public ResponseResult searchUserByNickName(String nickname) {
        //TODO % SQL injection need to be fixed
        List<User> users = userMapper.searchUserByNickName('%'+nickname+'%');
        return new ResponseResult<>(HttpStatus.OK.value(), users);
    }
}
