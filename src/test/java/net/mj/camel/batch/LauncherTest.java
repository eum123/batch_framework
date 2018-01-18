package net.mj.camel.batch;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class LauncherTest {

    @Test
    public void hello() {
        System.out.println("hello");
    }
	
}
