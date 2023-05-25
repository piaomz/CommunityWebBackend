package me.piaomz.piaomz;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import me.piaomz.piaomz.mapper.UserMapper;
import me.piaomz.piaomz.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    //@Autowired
    //private UserMapper userMapper;
    private static final String template = "Hello, %s!";
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(String.format(template, name));
    }
    @GetMapping("/greeting2")
    @PreAuthorize("hasAuthority('admin','test')")
    public Greeting greeting2(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(String.format(template, name));
    }


    /*
    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }*/
    /*@GetMapping("/greeting")
    public List<User> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        List<User> users = userMapper.selectUser();
        for(User user:users){
            System.out.println(user);
        }
        return users;
        //return new Greeting(String.format(template, name));
    }
    @GetMapping("/greeting2")
    public User greeting2(@RequestParam(value = "uid", defaultValue = "0") String uid) {
        User user = userMapper.queryUser(Integer.parseInt(uid));
        return user;
        //return new Greeting(String.format(template, name));
    }*/

}