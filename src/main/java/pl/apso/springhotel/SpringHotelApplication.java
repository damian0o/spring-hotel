package pl.apso.springhotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Application entry point
 */
@SpringBootApplication
public class SpringHotelApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringHotelApplication.class, args);
  }
}
