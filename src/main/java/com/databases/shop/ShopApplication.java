package com.databases.shop;

import com.databases.shop.models.Salesman;
import com.databases.shop.repositories.SalesmanFilterRepository;
import com.databases.shop.repositories.SalesmanRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;

@SpringBootApplication
public class ShopApplication implements CommandLineRunner {


	@Autowired
	private SalesmanRepository salesmanRepository;

	@Autowired
	private SalesmanFilterRepository salesmanFilterRepository;


	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		FileInputStream serviceAccount =
				new FileInputStream("src/main/resources/static/shopfrontend-56ebd-firebase-adminsdk-vijhq-47081e4c9a.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://shopfrontend-56ebd-default-rtdb.europe-west1.firebasedatabase.app")
				.build();

		FirebaseApp.initializeApp(options);

//		System.out.println("--------------------------------------------------");
//		SalesmanRepository.MinMaxOrderCount minMaxOrderCount = salesmanRepository.minMaxOrderCount();

//		System.out.println(minMaxOrderCount == null);
//		System.out.println(minMaxOrderCount.getMinCount());
//		System.out.println(minMaxOrderCount.getMaxCount());

//		salesmanRepository.salesmanFilter(150,2).forEach(System.out::println);

		salesmanFilterRepository.filterSalesmen(0,0,false).forEach(s -> System.out.println(s.getId()));

	}
}
