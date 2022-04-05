package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao  {

	Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
	   this.conn=conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		try {
			st=conn.prepareStatement(
					"INSERT INTO department (Name) values(?)"
			);
			
			st.setString(1, obj.getName());
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.getConnection();
		}
		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st=conn.prepareStatement(
			  "UPDATE department SET Name = ? WHERE Id = ?"
		    );
			
			
			st.setString(1, obj.getName());
			st.setInt(2,obj.getId() );
			
			st.executeUpdate();
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.getConnection();
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		
		
	}

	@Override
	public Department findById(Integer id) {
		
		return null;
	}

	@Override
	public List<Department> findAll() {
		
		return null;
	}

}
