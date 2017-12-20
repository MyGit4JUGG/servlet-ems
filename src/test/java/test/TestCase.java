package test;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import dao.EmployeeDAO;
import entity.Employee;
import util.DBUtil;

public class TestCase {
	
	@Test
	public void test1() throws SQLException{
		System.out.println(
				DBUtil.getConnection());
	}
	
	@Test
	public void test2(){
		EmployeeDAO dao = new EmployeeDAO();
		Employee e = new Employee();
		e.setEname("ะกิย");
		e.setSalary(20000);
		e.setAge(22);
		dao.save(e);
	}
	
	@Test
	public void test3(){
		EmployeeDAO dao = new EmployeeDAO();
		List<Employee> employees = 
				dao.findAll();
		System.out.println(employees);
	}
	
	@Test
	public void test4(){
		EmployeeDAO dao = new EmployeeDAO();
		dao.delete(24);
	}
	
	@Test
	public void test5(){
		EmployeeDAO dao = new EmployeeDAO();
		Employee e = dao.findById(22);
		System.out.println(e);
	}
	
	@Test
	public void test6(){
		EmployeeDAO dao = new EmployeeDAO();
		Employee e = dao.findById(22);
		e.setSalary(e.getSalary() * 2);
		dao.update(e);
	}
	
	
	
}



