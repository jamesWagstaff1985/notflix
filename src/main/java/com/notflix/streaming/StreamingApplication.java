package com.notflix.streaming;

import com.notflix.streaming.services.StreamingServiceInitializer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@RequiredArgsConstructor
@EnableMongoRepositories
public class StreamingApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingApplication.class, args);
	}

	private final StreamingServiceInitializer initializer;

	@PostConstruct
	void init() {
		initializer.init();
	}

}
