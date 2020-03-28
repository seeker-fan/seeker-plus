package com.wf.seeker;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/***
 * 单元测试类**
 * 
 * @author Fan.W
 * @since 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeekerPlusApplicationTests {

	@org.junit.Test
	public void test() {
		System.out.println("hello seeker-plus");
	}

}
