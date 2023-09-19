package miljana.andric;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MiljanaAndricBe2Application {

	public static void main(String[] args) {
		SpringApplication.run(MiljanaAndricBe2Application.class, args);
	}

}
