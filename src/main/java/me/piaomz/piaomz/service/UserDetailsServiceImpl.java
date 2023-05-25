package me.piaomz.piaomz.service;

import me.piaomz.piaomz.mapper.UserMapper;
import me.piaomz.piaomz.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loginUser(username);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(user)){
            throw new RuntimeException("UserName not Found!");
        }
        List<String> list = null;
        if(user.getType().equals("admin")){
            list = new ArrayList<>(Arrays.asList("admin","test","user"));
        }else if(user.getType().equals("test")){
            list = new ArrayList<>(Arrays.asList("test","user"));
        }else if(user.getType().equals("user")){
            list = new ArrayList<>(Arrays.asList("user"));
        }

        //封装成UserDetails对象返回
        return new LoginUserDetails(user,list);

    }
}