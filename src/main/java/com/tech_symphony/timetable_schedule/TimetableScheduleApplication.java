package com.tech_symphony.timetable_schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class TimetableScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimetableScheduleApplication.class, args);
	}

}
