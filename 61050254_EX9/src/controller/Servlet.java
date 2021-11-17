package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import service.StudentService;
import model.Student;

@WebServlet("/")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private StudentService studentDAO;
    private List<Integer> numberIdUse = new ArrayList<>();
    public Servlet() {
        this.studentDAO = new StudentService();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertUser(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			default:
				listUser(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
	
	private void insertUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String name = request.getParameter("name");
		String gpa = request.getParameter("gpa");
		Student newUser = new Student(name, gpa);
		studentDAO.insertUser(newUser);
		response.sendRedirect("list");
	}
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
			int id = Integer.parseInt(request.getParameter("id"));
			Integer idForDeleteId = Integer.valueOf(id);
			String name = request.getParameter("name");
			String gpa = request.getParameter("gpa");

			Student book = new Student(id, name, gpa);
			
			System.out.println(numberIdUse);
			numberIdUse.remove(idForDeleteId);
			studentDAO.updateUser(book);
			response.sendRedirect("list");
		
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		studentDAO.deleteUser(id);
		response.sendRedirect("list");

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Student> listUser = studentDAO.selectAllUsers();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("student-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("student-form.jsp");
		dispatcher.forward(request, response);
	}
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		  synchronized(getServletContext()) {
			int id = Integer.parseInt(request.getParameter("id"));
			if(numberIdUse.isEmpty() || !numberIdUse.contains(id)) {
				Student existingUser = studentDAO.selectUser(id);
				RequestDispatcher dispatcher = request.getRequestDispatcher("student-form.jsp");
				request.setAttribute("student", existingUser);
				numberIdUse.add(id);
				System.out.print(numberIdUse);
				dispatcher.forward(request, response);
			}else {
				dispatcher.forward(request, response);
			}
		  }
		
	

	}
	


}
