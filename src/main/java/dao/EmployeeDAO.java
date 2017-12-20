package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Employee;
import util.DBUtil;

public class EmployeeDAO {
	public void update(Employee e){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "UPDATE t_emp SET ename=?,"
					+ "salary=?,age=? WHERE id=?";
			PreparedStatement ps = 
				conn.prepareStatement(sql);
			ps.setString(1, e.getEname());
			ps.setDouble(2, e.getSalary());
			ps.setInt(3, e.getAge());
			ps.setInt(4, e.getId());
			ps.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}finally{
			DBUtil.closeConnection(conn);
		}
		
	}
	
	public Employee findById(int id){
		Employee e = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM t_emp "
					+ "WHERE id = ?";
			PreparedStatement ps = 
					conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				e = new Employee();
				e.setId(rs.getInt("id"));
				e.setEname(rs.getString("ename"));
				e.setSalary(rs.getDouble("salary"));
				e.setAge(rs.getInt("age"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}finally{
			DBUtil.closeConnection(conn);
		}
		return e;
	}
	
	public void delete(int id){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "DELETE FROM t_emp "
					+ "WHERE id = ?";
			PreparedStatement ps = 
					conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			DBUtil.closeConnection(conn);
		}
	}
	
	public List<Employee> findAll(){
		List<Employee> employees = 
				new ArrayList<Employee>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM t_emp";
			PreparedStatement ps = 
					conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Employee e = new Employee();
				e.setId(rs.getInt("id"));
				e.setEname(rs.getString("ename"));
				e.setSalary(rs.getDouble("salary"));
				e.setAge(rs.getInt("age"));
				employees.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			DBUtil.closeConnection(conn);
		}
		return employees;
	}
	
	
	
	
	public void save(Employee e){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = 
					"INSERT INTO t_emp "
				+ "VALUES(t_emp_seq.nextval,?,?,?)";
			PreparedStatement ps = 
				conn.prepareStatement(sql);
			ps.setString(1, e.getEname());
			ps.setDouble(2, e.getSalary());
			ps.setInt(3, e.getAge());
			ps.executeUpdate();
			
		} catch (SQLException e1) {
			/*
			 * step1.����־(�����ֳ�)
			 */
			e1.printStackTrace();
			/*
			 * step2.�鿴�쳣�Ƿ��ܹ������ָ���
			 * ������ܹ��ָ����������ݿ����ֹͣ��
			 * �����жϵȣ��������쳣���ǿ��Գ�֮Ϊ
			 * ϵͳ�쳣��,��ʾ�û��Ժ����ԡ�����ܹ�
			 * �ָ����������ָ���
			 */
			throw new RuntimeException(e1);
		}finally{
			DBUtil.closeConnection(conn);
		}
	}
}








