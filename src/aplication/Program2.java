package aplication;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		DepartmentDao departdao =DaoFactory.createDapartment();
		
		System.out.println("== Test 1: Insert Department ==");
		Department dep = new Department();
		dep.setName("Medicine");
		departdao.insert(dep);

	}

}
