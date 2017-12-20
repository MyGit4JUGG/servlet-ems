package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeeDAO;
import entity.Employee;

public class SomeServlet extends HttpServlet{
	public void service(HttpServletRequest request,
			HttpServletResponse response)
	throws ServletException,IOException{
		System.out.println("service()");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType(
				"text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//获得请求资源路径
		String uri = request.getRequestURI();
		System.out.println("uri:" + uri);
		//分析请求资源路径
		String action = 
			uri.substring(
					uri.lastIndexOf("/"),
					uri.lastIndexOf("."));
		System.out.println("action:" + action);
		//依据分析结果做不同的处理
		if("/list".equals(action)){
			System.out.println("处理员工列表请求");
			
			//查询出所有员工的信息
			EmployeeDAO dao = new EmployeeDAO();
			try{
				List<Employee> employees = 
						dao.findAll();
				
				//转发给listEmp.jsp来输出表格
				request.setAttribute("employees", 
						employees);
				RequestDispatcher rd = 
					request.getRequestDispatcher(
								"/WEB-INF/listEmp.jsp");
				rd.forward(request, response);
				System.out.println("转发之后的代码...");
			}catch(Exception e){
				e.printStackTrace();
				out.println("系统繁忙，稍后重试");
			}
			
		}else if("/toAdd".equals(action)){
			//转发到添加员工页面
			request.getRequestDispatcher(
					"/WEB-INF/addEmp.jsp")
			.forward(request, response);
			
		}else if("/add".equals(action)){
			System.out.println("处理添加员工请求");
			
			//读取员工信息
			String ename = request.getParameter("ename");
			String salary = request.getParameter("salary");
			String age = request.getParameter("age");
			System.out.println("ename:" + ename 
					+ " salary:" + salary 
					+ " age:" + age);
			
			//将员工信息插入到数据库
			EmployeeDAO dao = new EmployeeDAO();
			
			Employee e = new Employee();
			e.setEname(ename);
			e.setSalary(Double.parseDouble(salary));
			e.setAge(Integer.parseInt(age));
			try{
				dao.save(e);
				//重定向到员工列表
				response.sendRedirect("list.do");
				System.out.println("添加成功...");
			}catch(Exception e1){
				e1.printStackTrace();
				out.println("系统繁忙，稍后重试");
			}
		}else if("/del".equals(action)){
			System.out.println("处理删除员工请求");
			
			//读取要删除的员工的id
			String id = request.getParameter("id");
			try{
				//删除数据库中的记录
				EmployeeDAO dao = new EmployeeDAO();
				dao.delete(Integer.parseInt(id));
				//重定向到员工列表
				response.sendRedirect("list.do");
			}catch(Exception e){
				e.printStackTrace();
				out.println("系统繁忙，稍后重试");
			}
		}else if("/load".equals(action)){
			//读取要修改的员工的id
			String id = request.getParameter("id");
			//依据id查询数据库(将要修改的员工信息查询出来)
			EmployeeDAO dao = new EmployeeDAO();
			try{
				Employee e = 
						dao.findById(
								Integer.parseInt(id));
				
				//转发给updateEmp.jsp来生成修改页面
				request.setAttribute("e", e);
				request.getRequestDispatcher(
						"/WEB-INF/updateEmp.jsp")
				.forward(request, response);
				
			}catch(Exception e){
				e.printStackTrace();
				out.println("系统繁忙，稍后重试");
			}
		}else if("/update".equals(action)){
			//读取员工信息
			String id = request.getParameter("id");
			String ename = request.getParameter("ename");
			String salary = request.getParameter("salary");
			String age = request.getParameter("age");
			
			//修改数据库中的员工信息
			EmployeeDAO dao = new EmployeeDAO();
			try{
				Employee e = new Employee();
				e.setId(Integer.parseInt(id));
				e.setEname(ename);
				e.setSalary(Double.parseDouble(salary));
				e.setAge(Integer.parseInt(age));
				dao.update(e);
				//重定向到员工列表
				response.sendRedirect("list.do");
			}catch(Exception e){
				e.printStackTrace();
				out.println("系统繁忙，稍后重试");
			}
		}
	}
}




