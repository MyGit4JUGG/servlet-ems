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
		
		//���������Դ·��
		String uri = request.getRequestURI();
		System.out.println("uri:" + uri);
		//����������Դ·��
		String action = 
			uri.substring(
					uri.lastIndexOf("/"),
					uri.lastIndexOf("."));
		System.out.println("action:" + action);
		//���ݷ����������ͬ�Ĵ���
		if("/list".equals(action)){
			System.out.println("����Ա���б�����");
			
			//��ѯ������Ա������Ϣ
			EmployeeDAO dao = new EmployeeDAO();
			try{
				List<Employee> employees = 
						dao.findAll();
				
				//ת����listEmp.jsp��������
				request.setAttribute("employees", 
						employees);
				RequestDispatcher rd = 
					request.getRequestDispatcher(
								"/WEB-INF/listEmp.jsp");
				rd.forward(request, response);
				System.out.println("ת��֮��Ĵ���...");
			}catch(Exception e){
				e.printStackTrace();
				out.println("ϵͳ��æ���Ժ�����");
			}
			
		}else if("/toAdd".equals(action)){
			//ת�������Ա��ҳ��
			request.getRequestDispatcher(
					"/WEB-INF/addEmp.jsp")
			.forward(request, response);
			
		}else if("/add".equals(action)){
			System.out.println("�������Ա������");
			
			//��ȡԱ����Ϣ
			String ename = request.getParameter("ename");
			String salary = request.getParameter("salary");
			String age = request.getParameter("age");
			System.out.println("ename:" + ename 
					+ " salary:" + salary 
					+ " age:" + age);
			
			//��Ա����Ϣ���뵽���ݿ�
			EmployeeDAO dao = new EmployeeDAO();
			
			Employee e = new Employee();
			e.setEname(ename);
			e.setSalary(Double.parseDouble(salary));
			e.setAge(Integer.parseInt(age));
			try{
				dao.save(e);
				//�ض���Ա���б�
				response.sendRedirect("list.do");
				System.out.println("��ӳɹ�...");
			}catch(Exception e1){
				e1.printStackTrace();
				out.println("ϵͳ��æ���Ժ�����");
			}
		}else if("/del".equals(action)){
			System.out.println("����ɾ��Ա������");
			
			//��ȡҪɾ����Ա����id
			String id = request.getParameter("id");
			try{
				//ɾ�����ݿ��еļ�¼
				EmployeeDAO dao = new EmployeeDAO();
				dao.delete(Integer.parseInt(id));
				//�ض���Ա���б�
				response.sendRedirect("list.do");
			}catch(Exception e){
				e.printStackTrace();
				out.println("ϵͳ��æ���Ժ�����");
			}
		}else if("/load".equals(action)){
			//��ȡҪ�޸ĵ�Ա����id
			String id = request.getParameter("id");
			//����id��ѯ���ݿ�(��Ҫ�޸ĵ�Ա����Ϣ��ѯ����)
			EmployeeDAO dao = new EmployeeDAO();
			try{
				Employee e = 
						dao.findById(
								Integer.parseInt(id));
				
				//ת����updateEmp.jsp�������޸�ҳ��
				request.setAttribute("e", e);
				request.getRequestDispatcher(
						"/WEB-INF/updateEmp.jsp")
				.forward(request, response);
				
			}catch(Exception e){
				e.printStackTrace();
				out.println("ϵͳ��æ���Ժ�����");
			}
		}else if("/update".equals(action)){
			//��ȡԱ����Ϣ
			String id = request.getParameter("id");
			String ename = request.getParameter("ename");
			String salary = request.getParameter("salary");
			String age = request.getParameter("age");
			
			//�޸����ݿ��е�Ա����Ϣ
			EmployeeDAO dao = new EmployeeDAO();
			try{
				Employee e = new Employee();
				e.setId(Integer.parseInt(id));
				e.setEname(ename);
				e.setSalary(Double.parseDouble(salary));
				e.setAge(Integer.parseInt(age));
				dao.update(e);
				//�ض���Ա���б�
				response.sendRedirect("list.do");
			}catch(Exception e){
				e.printStackTrace();
				out.println("ϵͳ��æ���Ժ�����");
			}
		}
	}
}




