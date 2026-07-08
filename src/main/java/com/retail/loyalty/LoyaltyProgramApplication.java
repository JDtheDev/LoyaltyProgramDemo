package com.retail.loyalty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class LoyaltyProgramApplication
{
	public static void main(String[] args) throws UnknownHostException {

		System.out.println("Starting server");
		System.out.printf("Hostname: %s%n", InetAddress.getLocalHost().getHostName());
		System.out.printf("Address: %s%n", InetAddress.getLocalHost().getHostAddress());

		SpringApplication.run(LoyaltyProgramApplication.class, args);
	}
}
