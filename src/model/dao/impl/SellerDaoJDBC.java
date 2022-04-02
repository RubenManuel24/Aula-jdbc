package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
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
