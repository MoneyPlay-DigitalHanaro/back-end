package com.moneyplay.MoneyPlay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling

public class MoneyPlayApplication {


	public static void main(String[] args) {

		// 스케쥴링이 되면 ShuffleWord 실행


//
//		ShuffleWord shuffleWord = new ShuffleWord(wordTodayRepository,wordRepository);
//		shuffleWord.ShuffleWord();


		SpringApplication.run(MoneyPlayApplication.class, args);

	}

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

}
