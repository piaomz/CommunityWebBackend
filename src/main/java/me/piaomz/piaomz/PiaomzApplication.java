package me.piaomz.piaomz;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class PiaomzApplication {

	public static void main(String[] args) throws IOException {
		/*
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory =
				new SqlSessionFactoryBuilder().build(inputStream);

		try (SqlSession session = sqlSessionFactory.openSession()) {
			List<User> users = session.selectList("dev.selectUser");
			System.out.println(users.get(0).getUsername());
			session.close();
		}
*/

		ConfigurableApplicationContext run = SpringApplication.run(PiaomzApplication.class, args);
		System.out.println(run);
	}

}
