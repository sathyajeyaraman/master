package com.monitor.service;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * This program demonstrates how to use the Watch Service API to monitor change
 * events for a specific directory.
 */
@Service
public class DirectoryWatchService {
	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${directory.path}")
	private String directoryPath;
	
	@Value("${mail.toaddress}")
	private String emailToAddress;
	
	public void service() {

		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path dir = Paths.get(directoryPath);
			dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

			System.out.println("Watch Service registered for dir: " + dir.getFileName());
			// Read server.port from app.prop

			while (true) {
				WatchKey key;
				try {
					key = watcher.take();
				} catch (InterruptedException ex) {
					return;
				}

				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();

					@SuppressWarnings("unchecked")
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					Path fileName = ev.context();

					System.out.println(kind.name() + ": " + fileName);
					String emailContent = "Event kind:" + kind.name() 
			        + ". File affected: " + event.context() + ".";
					System.out.println(emailContent);
					sendEmail(emailContent);
				}

				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			}

		} catch (IOException ex) {
			System.err.println(ex);
		}

	}

	void sendEmail(String emailContent) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			String emailToAddresses[] = emailToAddress.split(",");
			for(String emailToAddress : emailToAddresses)
			System.out.println(emailToAddress);
			msg.setTo(emailToAddresses);
			msg.setSubject("Folder monitoring alert!");
			msg.setText(emailContent);

			javaMailSender.send(msg);
		} catch (MailException exception) {
			System.out.println("Unable to send the notification "+ exception);
			exception.printStackTrace();
		}

	}
}
