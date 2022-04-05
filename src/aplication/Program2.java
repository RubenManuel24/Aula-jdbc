package aplication;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		DepartmentDao departdao =DaoFactory.createDapartment();
		
		System.out.println("== Test 1: Insert Department ==");
		Department dep = new Department();
		dep.setName("Natation");
		//departdao.insert(dep);
		
		System.out.println("== Test 2: Insert Department ==");
		dep.setName("Desport");
		dep.setId(7);
		//departdao.update(dep);
		
		System.out.println("== Test 3: Delete Department for name ==");
		dep.setName("Natation");
		//departdao.deleteByNome(dep);
		
		System.out.println("\n== Test 4: FindAll Department for name ==");
		List<Department> list = new ArrayList<>();
		list=departdao.findAll();
		for(Department dep2 : list) {
			
			System.out.println(dep2);
		}

	}

}
