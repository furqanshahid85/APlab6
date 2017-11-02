package lab6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.jdbc.PreparedStatement;

public class persistentEcafe {
	public static void main(String[] args) throws SQLException {
		Scanner inputVal = new Scanner(System.in);
		int continu = 1;
		String pass = "asdf1234";
		String name = "root";
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecafe", name, pass);
		
		Statement statement = connection.createStatement();
		System.out.println("Welcome to The Cafe Westros ");
		System.out.println("Please enter your name: ");
		
		String userName,userAddr;
		int userContact;
		
		System.out.print("name: ");
		userName= inputVal.nextLine();
		
		String sqlUserInsert="insert into user (user_name) values (?)";
		PreparedStatement statementUserInsert = (PreparedStatement) connection.prepareStatement(sqlUserInsert);
		statementUserInsert.setString(1,userName);
		
		int userInsert = statementUserInsert.executeUpdate();
		
		while (continu != 0) {
			
			System.out.println("***** Select Your Order From the Menu: *******");
			ResultSet rs = statement.executeQuery("SELECT * FROM courses");
			while (rs.next()) {
				System.out.print(rs.getInt(1));
				System.out.print(" ");
				System.out.print(rs.getString(2));
				System.out.print("\n");
			}

			System.out.print("**** Select the item to order ***** ");
			int courseSelected = inputVal.nextInt();
			System.out.println();

			String sql = "SELECT * FROM item where course_no=?";
			PreparedStatement statementItems = (PreparedStatement) connection.prepareStatement(sql);
			statementItems.setInt(1, courseSelected);
			ResultSet rsItems = statementItems.executeQuery();
			
			while (rsItems.next()){
				System.out.print(rsItems.getInt(1));
				System.out.print("  ");
				System.out.print(rsItems.getString(2));
				System.out.print("  -----  ");
				System.out.print(rsItems.getString(3) + "Rs");
				System.out.println();
			}
			
			int itemSelected = inputVal.nextInt();
			System.out.println();
			
			String sqlItemVal="select * from item where item_no=?";
			PreparedStatement statementItemVal = (PreparedStatement) connection.prepareStatement(sqlItemVal);
			statementItemVal.setInt(1, itemSelected);
			ResultSet rsItemVal = statementItemVal.executeQuery();
			
			int itemPrice,prepTime;
			
			rsItemVal.next();
			itemPrice=rsItemVal.getInt(3);
			//rsItemVal.next();
			prepTime=rsItemVal.getInt(4);
			
			
			String sqlUserId="select userID from user where user_name=?";
			PreparedStatement statementUserId = (PreparedStatement) connection.prepareStatement(sqlUserId);
			statementUserId.setString(1,userName);
			ResultSet rsUserId = statementUserId.executeQuery();
			rsUserId.next();
			int userID=rsUserId.getInt(1);
			
			PreparedStatement statementOrder=(PreparedStatement) connection.prepareStatement("insert "
					+ "into order (item_price,prep_time,userID) values(?,?,?)");  
			statementOrder.setInt(1,itemPrice);//price
			statementOrder.setInt(2,prepTime);//prep time
			statementOrder.setInt(3,userID);//user id
			
			//int rsOrder = statementOrder.executeUpdate();
			
			System.out.print("Order Something Else[yes=1 / no=0] ");
			continu = inputVal.nextInt();
			if (continu == 0) {
			System.out.println("your order has been taken..");
			System.out.print("enter your address: ");
			userAddr=inputVal.nextLine();
			System.out.print("enter your contact no. : ");
			userContact=inputVal.nextInt();

/*String sqlUserInsert="insert into user (user_address,user_contact) values (?,?)";
PreparedStatement statementUserInsert = (PreparedStatement) connection.prepareStatement(sqlUserInsert);
statementUserInsert.setInt(2,userAddr);
statementUserInsert.setInt(3,userContact);*/
			}
		}
	}

}
