package com.Hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {



	private Connection connection;   // connection variable 
	
	
	public Doctor(Connection connection ) { 		// parameterized constructor
		this.connection=connection;
		
				

	}
	
	// making method of view doctor
	
	public  void viewdoctorts() { // no argument  constructor
		String query = "SELECT * FROM doctors";
		try {                        // using try catch block
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultset = (ResultSet) preparedStatement.executeQuery();  // print the value
			// format specifier to format the data but in sql like database
			System.out.println("doctor:  ");
			System.out.println("+------------+-----------------------------+------------+");
			System.out.println("|doctor id  | Name                        |specialization");
			System.out.println("+------------+-----------------------------+------------+");
			
			while (resultset.next()) {
				int id =  resultset.getInt("id"); //  column name 
				String name = resultset.getString("name");
				
				String specialization =  resultset.getString("specialization");
				System.out.printf("|%-14s|%-26s|%-11s| \n", id,name,specialization);  // formating the output
				System.out.println("+------------+-----------------------------+------------+");
				
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// making method get doctor id
	
	public  boolean getDoctorById(int id) {
		String query = "SELECT * FROM doctors WHERE id= ?";
		try {
			PreparedStatement preparedStatement =  connection.prepareStatement(query);
			 preparedStatement.setInt(1, id);
			 ResultSet resultset =  preparedStatement.executeQuery();
			if( resultset.next()) {
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
	


