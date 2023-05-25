package me.piaomz.piaomz;

import me.piaomz.piaomz.mapper.UserMapper;
import me.piaomz.piaomz.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class PiaomzApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private UserMapper usermapper;

	@Test
	public void testUserMapper(){
		List<User> users= usermapper.selectUser();
		System.out.println(users);
		System.out.println("111111");
	}

	@Autowired
	PasswordEncoder passwordEncoder;
	@Test
	public void testPasswordEncoder(){
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String crptpassword = passwordEncoder.encode("123456");
		System.out.println(crptpassword);
		crptpassword = passwordEncoder.encode("123456");
		System.out.println(passwordEncoder.matches("123456",crptpassword));
	}

}
