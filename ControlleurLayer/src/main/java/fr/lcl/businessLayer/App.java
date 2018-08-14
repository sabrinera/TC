package fr.lcl.businessLayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication//(scanBasePackages={"fr.lcl.businessLayer.service", "fr.lcl.businessLayer.dao"})
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}