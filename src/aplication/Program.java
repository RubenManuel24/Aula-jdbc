package aplication;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
	
		Department obj = new Department(12,"Book");
		Seller seller = new Seller(1,"Ruben","rubrn@gmail.com",new Date(),100000.00,obj);
		
		System.out.println(seller);
	}

}
