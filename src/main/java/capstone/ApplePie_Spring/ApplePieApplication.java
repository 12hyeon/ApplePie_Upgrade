package capstone.ApplePie_Spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // jpa auditing 활성화
@SpringBootApplication
public class ApplePieApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplePieApplication.class, args);

	}

}
