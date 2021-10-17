/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this tstulate file, choose Tools | Tstulates
 * and open the tstulate in the editor.
 */
package StudentDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class StudentDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        /*String derbyEmbeddedDriver = "org.apache.derby.jdbc.EmbeddedDriver";
        String msAccessDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
        String msSQlDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String oracleDriver = "oracle.jdbc.driver.OracleDriver";*/

        String derbyClientDriver = "org.apache.derby.jdbc.ClientDriver";
        //String mySqlDriver = "com.mysql.cj.jdbc.Driver";
        //load driver
        Class.forName(derbyClientDriver);
        //Class.forName(mySqlDriver);
        //create connection
        /*
         * String url="jdbc:mysql://server[:port]/databaseName"; //for mySQL
         * String url="jdbc:derby:databaseName"; //for DerbyEmbedded
         * String url= "jdbc:odbc:Driver=:datasourceNameOfODBC" //for MS Accces
         * String url= "jdbc:sqlserver://server[:port]:database="databaseName" //for MS SQL Server 
         * String url= "jdbc:oracle:thin:@server:port:databaseName" //for Oracle
         */
        String url = "jdbc:derby://localhost:1527/Student";
        //String url="jdbc:mysql://localhost:3306/Student?serverTimezone=UTC";
        String user = "app";
        String passwd = "app";

        Connection con = DriverManager.getConnection(url, user, passwd);
        //create statement
       Statement stmt = con.createStatement();
       Student stu1 = new Student(1, "John", 2.1);
       Student stu2 = new Student(2, "Marry", 4.0);
//       insertStudent(stmt, stu1);
//       insertStudent(stmt, stu2);
       //Student stu = getStudentById(stmt, 1);
       //stu.setGpa(12346);
        //updateStudentSalary(stmt, stu);
        //stu.setName("Jack");
        //updateStudentName(stmt, stu);
        //deleteStudent(stmt, stu);
        //Student stu3 = new Student(3,"Markus", 14578);
        //Student stu4 = new Student(4,"Mark", 24579);
        insertStudentPreparedStatement(con, stu1);
        insertStudentPreparedStatement(con, stu2);

        //Student stu = getStudentByIdPreparedStatement(con, 1);
        //stu.setName("Jack");
        //stu.setGpa(98765);
        //updateStudentNamePreparedStatement(con, stu);
        //updateStudentSalaryPreparedStatement(con, stu);
        //deleteStudentPreparedStatement(con, stu);

        //ArrayList<Student> StudentList = getAllStudent(con);
        //printAllStudent(StudentList);
        //close connection
        stmt.close();
        con.close();
    }
    public static void printAllStudent(ArrayList<Student> StudentList) {
        for(Student stu : StudentList) {
           System.out.print(stu.getId() + " ");
           System.out.print(stu.getName() + " ");
           System.out.println(stu.getGpa() + " ");
       }
    }
    
    public static ArrayList<Student> getAllStudent (Connection con) throws SQLException {
        String sql = "select * from Student order by id";
        PreparedStatement ps = con.prepareStatement(sql);
        ArrayList<Student> StudentList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
           Student Student = new Student();
           Student.setId(rs.getInt("id"));
           Student.setName(rs.getString("name"));
           Student.setGpa(rs.getDouble("gpa"));
           StudentList.add(Student);
       }
       rs.close();
       return StudentList;
       
    }
    
   public static Student getStudentById(Statement stmt, int id) throws SQLException {
       Student stu = null;
       String sql = "select * from Student where id = " + id;
       ResultSet rs = stmt.executeQuery(sql);
       if (rs.next()) {
           stu = new Student();
           stu.setId(rs.getInt("id"));
           stu.setName(rs.getString("name"));
           stu.setGpa(rs.getDouble("gpa"));
       }
       return stu;
   } 
   public static void insertStudent(Statement stmt, Student stu) throws SQLException {
       /*String sql = "insert into Student (id, name, gpa)" +
                     " values (5, 'Mark', 12345)";*/
        String sql = "insert into Student (id, name, gpa)" +
                     " values (" + stu.getId() + "," + "'" + stu.getName() + "'" + "," + stu.getGpa() + ")";
        int result = stmt.executeUpdate(sql);
        System.out.println("Insert " + result + " row");
   } 
   public static void deleteStudent(Statement stmt, Student stu) throws SQLException {
       String sql = "delete from Student where id = " + stu.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("delete " + result + " row");
   }
   public static void updateStudentSalary(Statement stmt, Student stu) throws SQLException {
       String sql = "update Student set gpa  = " + stu.getGpa() + 
               " where id = " + stu.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
   }
   public static void updateStudentName(Statement stmt, Student stu) throws SQLException {
       String sql = "update Student set name  = '" + stu.getName() + "'" + 
               " where id = " + stu.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
   }
   
   public static void insertStudentPreparedStatement(Connection con, Student stu) throws SQLException {
       String sql = "insert into Student (id, name, gpa)" + 
               " values (?,?,?)";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, stu.getId());
       ps.setString(2, stu.getName());
       ps.setDouble(3, stu.getGpa());
       int result = ps.executeUpdate();
        //display result
        System.out.println("Insert " + result + " row");
   }
   public static void deleteStudentPreparedStatement(Connection con, Student stu) throws SQLException {
       String sql ="delete from Student where id = ?";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, stu.getId());
       int result = ps.executeUpdate();
        //display result
        System.out.println("Delete " + result + " row");
   }
   public static void updateStudentSalaryPreparedStatement(Connection con, Student stu) throws SQLException {
       String sql = "update Student set gpa  = ? where id = ? ";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setDouble(1, stu.getGpa());
       ps.setInt(2, stu.getId());
       int result = ps.executeUpdate();
        //display result
        System.out.println("update " + result + " row");
   }
   public static void updateStudentNamePreparedStatement(Connection con, Student stu) throws SQLException {
       String sql = "update Student set name  = ? where id = ? ";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setString(1, stu.getName());
       ps.setInt(2, stu.getId());
       int result = ps.executeUpdate();
        //display result
        System.out.println("update " + result + " row");
   }
   public static Student getStudentByIdPreparedStatement(Connection con, int id) throws SQLException {
       Student stu = null;
       String sql = "select * from Student where id = ?";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, id);
       ResultSet rs = ps.executeQuery();
       if (rs.next()) {
           stu = new Student();
           stu.setId(rs.getInt("id"));
           stu.setName(rs.getString("name"));
           stu.setGpa(rs.getDouble("gpa"));
       }
       return stu;
   }
}
