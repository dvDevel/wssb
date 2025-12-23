package lab.john.wssb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WssbApplication {

	public static void main(String[] args) {
		SpringApplication.run(WssbApplication.class, args);
	}

}
