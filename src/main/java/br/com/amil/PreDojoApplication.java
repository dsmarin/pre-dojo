package br.com.amil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.amil.dao.MatchDao;
import br.com.amil.dao.impl.MatchDaoImpl;
import br.com.amil.service.GameMatchService;
import br.com.amil.service.impl.GameMatchServiceImpl;

@SpringBootApplication
public class PreDojoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreDojoApplication.class, args);
	}
	
}
