package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
			  "UPDATE department SET Name = ? WHERE id = ?"
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
	public void deleteByNome(Department obj) {
		PreparedStatement st = null;
		try {
			st=conn.prepareStatement(
				"DELETE FROM department WHERE Name = ? limit 1"
			);
			
			st.setString(1, obj.getName());
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
	public Department findById(Integer id) {
		
		return null;
	}

	@Override 
	public List<Department> findAll() {
		  PreparedStatement st = null;
		  ResultSet rs =null;
		  Department dep = new Department();
		  try {
			st=conn.prepareStatement(
			 "SELECT * FROM department"
			);
			rs=st.executeQuery();
			dep.setId(rs.getInt("Id"));
		    dep.setName(rs.getString("Name"));
			
		    List <Department> list =new ArrayList<>();
            while(rs.next()==true) {
            	list.add(dep);
            }
            return list;
	    }
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
	    }

   }
}





