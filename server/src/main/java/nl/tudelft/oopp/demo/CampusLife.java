package nl.tudelft.oopp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "nl.tudelft.oopp.demo.repositories")
@SpringBootApplication
public class CampusLife {

    public static void main(String[] args) {
        SpringApplication.run(CampusLife.class, args);
    }

}
