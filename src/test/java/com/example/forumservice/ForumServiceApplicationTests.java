package com.example.forumservice;

import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ContextConfiguration(locations = {
    "classpath*:spring/applicationContext.xml",
    "classpath*:spring/applicationContext-jpa.xml",
    "classpath*:spring/applicationContext-security.xml"
})
class ForumServiceApplicationTests {
	
	@Test
	void contextLoads() {
	}

}
