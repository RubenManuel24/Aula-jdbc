package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

	void insert(Department obj);
	void update(Department obj);
    Department findById(Integer id);
    List<Department> findAll();
	void deleteByNome(Department dep);
}
