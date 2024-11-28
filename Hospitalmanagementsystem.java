package com.Hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Hospitalmanagementsystem {
	
		private static final String url ="jdbc:mysql://localhost:3306/hospital";
		private static final String username = "root";
		private static final String password = "root";
		private static final String appointmentDate = "";
		
		public static void main(String[] args) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
				
			}
			Scanner scanner = new Scanner(System.in);
			
			try {
				Connection connection = DriverManager.getConnection(url,username,password);
				
				Patient patient = new Patient(connection, scanner);
				Doctor doctor = new Doctor(connection);
				while(true) {
					System.out.println("HOSPITAl MANAGEMENT SYSTEM");
					System.out.println("1. Add Patient");
					System.out.println("2. View Patients");
					System.out.println("3. View Doctors");
					System.out.println("4. Book Appointmet");
					System.out.println("5. Exit");
					System.out.println("enter your choice: ");
					Scanner scanner1 = null;
					int choice = scanner.nextInt();
					
					switch(choice) {
					case 1:
						// Add patient
						patient.addPatient();
						break;
					
					case 2:
						// view Patients
						patient.viewPatients();
						break;
						
					case 3:
						//view Doctors
						doctor.viewdoctorts();
						break;
						
					case 4:
						//Book Appointment
						
					case 5:
						return;
					default:
						System.out.println("Enter valid choice !!!");
					}
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void bookAppointment(Patient patient , Doctor doctor ,Connection connection , Scanner scanner) {
			System.out.println("Enter Patient ID: ");
			int patientid = scanner.nextInt();
			System.out.println("Enter doctor ID:");
			int doctorId = scanner.nextInt();
			System.out.println("enter appointment date (YYYY-MM-DD):");
			String appointment = scanner.next();
			if(patient.getPatientById(patientid)  && doctor.getDoctorById(doctorId)) {
				if(checkDoctorAvailability(doctorId , appointmentDate)) {
					String appointmentQuery = "INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES(?,?,?)";
					try {
						PreparedStatement preparedstatement = connection.prepareStatement(appointmentQuery);
						preparedstatement.setInt(1,patientid);
						preparedstatement.setInt(2,doctorId);
						preparedstatement.setString(3,appointment);
						int rowsAffected = preparedstatement.executeUpdate();
						if(rowsAffected >0) {
							System.out.println("Appointment Booked!");
						}else {
							System.out.println("failed to book Appointment!");
						}
						
					}catch(SQLException e) {
						e.printStackTrace();
						
					}
					
				}else {
					System.out.println("Doctor not available on this date!!");
				}
			}else {
				System.out.println("either doctor or patient doesn;t exist !!!");
			}
		}

		private static boolean checkDoctorAvailability(int doctorId, String appointmentdate2) {
			String query = " SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
			try {
				Connection connection = null;
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, doctorId);
				preparedStatement.setString(2, appointmentDate);
				ResultSet resultset = preparedStatement.executeQuery();
				if(resultset.next()) {
					int count = resultset.getInt(1);
					if(count ==0) {
						return true;
					}else {
						return false;
					}
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
				
			}
			return false;
		}

}
