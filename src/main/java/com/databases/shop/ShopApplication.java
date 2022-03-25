package com.databases.shop;

import com.databases.shop.models.Salesman;
import com.databases.shop.repositories.*;
import com.databases.shop.services.interfaces.ProductInOrderService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ShopApplication implements CommandLineRunner {


	@Autowired
	private SalesmanRepository salesmanRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private SalesmanRepository customerRepository;

	@Autowired
	private SalesmanFilterRepository salesmanFilterRepository;

	@Autowired
	private CustomerFilterRepository customerFilterRepository;

	@Autowired
	private OrderFilterRepository orderFilterRepository;


	@Autowired
	private ProductInOrderService productInOrderService;


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

//		salesmanFilterRepository.filterSalesmen(0,0,false).forEach(s -> System.out.println(s.getId()));

//		customerFilterRepository.filterCustomers(0,0,-1,-1,0).forEach(s -> System.out.println(s.getId()));



		//http://localhost:8081/orders/filter?statuses=DONE,NEW,IN_PROGRESS
		//    &prodNameNum=0&catNum=0&orderCost=0&dateFilterEnabled=false
		//    &date=1970-01-01&salesmanId=-1&customerId=-1&badProviderId=-1&orderId=-1
		//    &hasAllNotThoseProds=false&providerId=-1&prodNumK=0
//		String[] statuses = new String[3];
//
//		statuses[0] = "DONE";
//		statuses[1] = "NEW";
//		statuses[2] = "IN_PROGRESS";
//
//
//		orderFilterRepository
//				.filterOrders(List.of("DONE","NEW","IN_PROGRESS"),0,
//						0,0,
//						"1970-01-01",false,
//						-1,-1,
//						-1,-1,
//						-1,0).forEach(o -> System.out.println(o.getId()));


//		System.out.println(salesmanRepository.existsByEmail("illiasitkov@gmail.com"));
//
//		System.out.println(customerRepository.existsByEmail("illiasitkov@gmail.com"));


//		System.out.println(orderRepository.getByCustomerEmail("wfewev@wwf.wef"));


//		productInOrderService.updateProductQuantityInOrder(20L,0L,190);


	}

}
