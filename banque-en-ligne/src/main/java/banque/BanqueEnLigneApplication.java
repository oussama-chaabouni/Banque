package banque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;



@EnableScheduling
@EnableSwagger2
@SpringBootApplication
public class BanqueEnLigneApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanqueEnLigneApplication.class, args);
	}

}
