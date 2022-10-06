package com.databases.shop;

import com.databases.shop.repositories.*;
import com.databases.shop.services.implementations.ReportServiceImpl;
import com.databases.shop.services.interfaces.ProductInOrderService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.FileInputStream;

@SpringBootApplication(scanBasePackages = "com.databases")
public class ShopApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		FileInputStream serviceAccount =
				new FileInputStream("online-shop/src/main/resources/static/shopfrontend-56ebd-firebase-adminsdk-vijhq-47081e4c9a.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://shopfrontend-56ebd-default-rtdb.europe-west1.firebasedatabase.app")
				.build();

		FirebaseApp.initializeApp(options);
	}

}
