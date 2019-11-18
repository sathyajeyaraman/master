package com.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.monitor.service.DirectoryWatchService;

@SpringBootApplication
public class DirectoryMonitorApplication implements CommandLineRunner {
	@Value("${directory.path}")
	private String directoryPath;
	
	@Autowired
	DirectoryWatchService watchService;

	public static void main(String[] args) {
		SpringApplication.run(DirectoryMonitorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		watchService.service();
	}

	
}
