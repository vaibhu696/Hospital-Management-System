package com.Hospitalmanagementsystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Patient {
	
		private Connection connection;   // connection variable 
		private Scanner scanner;
		
		public Patient(Connection connection ,Scanner scanner) { 		// parameterized constructor
			this.connection=connection;
			this.scanner=scanner;
					

		}
		
		// making method of add patient
		public void addPatient() {
			System.out.print("enter patient name: ");     // 3 things we taking  user from input
			String name = scanner.next();                    // name
			String name1 = scanner.nextLine();
			System.out.print("enter patient age: ");     
			int age = scanner.nextInt();                   //age
			System.out.print("enter patient gender:  ");
			String gender = scanner.next();                //gender
			
			try {            // we are using try catch to connect the database
				String query = "INSERT INTO patients(name, age, gender) VALUES (?, ?, ?)";
				PreparedStatement preparedStatement = connection.prepareStatement(query);  // connection instance have prepared statement method sql query as a argument pass we can run 
				preparedStatement.setString(1, name1);
				preparedStatement.setInt(2, age);
				preparedStatement.setString(3, gender);
				
				// by using Exceute update method which has prepared statement instance this data will give integer value 
				int affectedRows = preparedStatement.executeUpdate();
				if(affectedRows >0) {
					System.out.println("patient add successfully !!!");
				}else {
					System.out.println("failed to add patient");
				}
				
				
			}catch(SQLException e) {  // e is instance
				e.printStackTrace();
			}
			
		}
		
		// making method of view patient
		
		public void viewPatients() { // no argument  constructor
			String query = "SELECT * FROM patients";
			try {                        // using try catch block
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultset = preparedStatement.executeQuery();  // print the value
				// format specifier to format the data but in sql like database
				System.out.println("Patients:  ");
				System.out.println("+------------+-----------------------------+------------+-----------+");
				System.out.println("|Patient id  | Name                        | Age       | Gender   |");
				System.out.println("+------------+-----------------------------+------------+-----------+");
				
				while(resultset.next()) {
					int id = resultset.getInt("id"); //  column name 
					String name = resultset.getString("name");
					int age = resultset.getInt("age"); //  column name 
					String gender = resultset.getString("gender");
					System.out.printf("|%-14s|%-26s|%-11s|%-11s|\n",id ,name,age,gender);  // formating the output
					System.out.println("+------------+-----------------------------+------------+-----------+");
					
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// making method get patient id
		
		public boolean getPatientById(int id) {
			String query = "SELECT * FROM patients WHERE id= ?";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, id);
				java.sql.ResultSet resultset = preparedStatement.executeQuery();
				if(resultset.next()) {
					return true;
				}else {
					return false;
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return false;
		}





}
