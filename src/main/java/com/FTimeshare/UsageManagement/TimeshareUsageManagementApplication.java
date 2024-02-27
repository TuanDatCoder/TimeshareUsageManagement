package com.FTimeshare.UsageManagement;

import com.FTimeshare.UsageManagement.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SpringBootApplication
@EntityScan(basePackages = "com.FTimeshare.UsageManagement.entities")
@EnableJpaRepositories(basePackages = "com.FTimeshare.UsageManagement.repositories")
@EnableTransactionManagement
@ComponentScan("com.FTimeshare")
public class TimeshareUsageManagementApplication {


	public static void main(String[] args) {

		SpringApplication.run(TimeshareUsageManagementApplication.class, args);
	}

}
