package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
    
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn){
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null; 
		try {
			st=conn.prepareStatement(
		      "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId)"
		      + "VALUES"
		      + "(?, ?, ?, ?, ?)",
			  Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffercted = st.executeUpdate();
			
			if(rowsAffercted > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()==true) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			
			else {
				throw new DbException("Unexpected erro! No rows affected!");
			}
	
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
		
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
             "UPDATE seller\r\n"
             + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?\r\n"
             + "WHERE id = ?;\r\n"
             + ""
			);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
			"SELECT s.*, d.name FROM seller AS s join department AS d ON s.DepartmentId = d.id WHERE s.id = ?"				
		    );
			
			st.setInt(1, id);
			rs = st.executeQuery();
		 	if(rs.next()==true){
		 		
		 		Department dep = instatiteDepartment(rs);
		 		Seller obj = instatiteSeller(rs,dep);
		 		return obj;
		 	}
		 	return null;
		}
		catch(SQLException e) {
			
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
	}

	private Seller instatiteSeller(ResultSet rs, Department dep) throws SQLException {
	    Seller obj = new Seller();
		obj.setId(rs.getInt("id"));
 		obj.setName(rs.getString("Name"));
 		obj.setEmail(rs.getString("Email"));
 		obj.setBaseSalary(rs.getDouble("BaseSalary"));
 		obj.setBirthDate(rs.getDate("BirthDate"));
 		obj.setDepartment(dep);
 		return obj;
 		}

	private Department instatiteDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
 		dep.setName(rs.getString("Name"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st=conn.prepareStatement(
			"select s.*, d.Name\r\n"
			+ "from seller as s join department as d\r\n"
			+ "on s.DepartmentId = d.Id\r\n"
			+ "order by s.name;");
				
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				
			Department dep = map.get(rs.getInt("DepartmentId"));
			
			if(dep == null) {
			
				dep = instatiteDepartment(rs);
				map.put(rs.getInt("DepartmentId"), dep);
			}
			Seller obj = instatiteSeller(rs, dep);
				list.add(obj);
			}
			return list;
		}
		catch(SQLException e) {
			
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st=conn.prepareStatement(
			"select s.*, d.Name\r\n"
			+ "from seller as s join department as d\r\n"
			+ "on s.DepartmentId = d.Id\r\n"
			+ "where DepartmentId = ?\r\n"
			+ "order by s.name;");
				
			st.setInt(1,department.getId());
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				
			Department dep = map.get(rs.getInt("DepartmentId"));
			
			if(dep == null) {
			
				dep = instatiteDepartment(rs);
				map.put(rs.getInt("DepartmentId"), dep);
			}
			Seller obj = instatiteSeller(rs, dep);
				list.add(obj);
			}
			return list;
		}
		catch(SQLException e) {
			
			throw new DbException(e.getMessage());
		}
		
	}
	
	
	
	
	
	
	
	
	

}
