package Hospital.Management.System;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "naman210128";

    public static void main(String[] args) {
        //Main function to connect jdbc driver.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection,scanner);
            Doctor doctor =  new Doctor(connection);
            while (true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient ");
                System.out.println("2 .View Patient ");
                System.out.println("3. View Doctor ");
                System.out.println("4. Book Appointment ");
                System.out.println("5. Exit ");
                System.out.println("Enter your choice: ");
                int choice  = scanner.nextInt();

                switch (choice){                //Switch loop for choice.
                    case 1:
                        //Add Patient
                        patient.addPatient();           //Adding patients data.
                        System.out.println();
                        break;
                    case 2:
                        //View Patient
                        patient.viewPatients();         //View Patients.
                        System.out.println();
                        break;
                    case 3:
                        //vies Doctor
                        doctor.viewDoctor();            //View Doctor.
                        System.out.println();
                        break;
                    case 4:
                        //Bool Appointment              //Booked Appointment.
                        bookAppointment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        //Exit
                        System.out.println("Thank You for Using System!!");
                        return;
                    default:
                        System.out.println("Enter Valid choice!!");
                        break;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Book Appointment by function or method.
    public static void bookAppointment(Patient patient,Doctor doctor, Connection connection, Scanner scanner){
        System.out.println("Enter Patient Id: ");
        int patientId =  scanner.nextInt();

        System.out.println("Enter Doctor Id: ");
        int doctorId = scanner.nextInt();

        System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
        String date = scanner.next();

        if(patient.checkPatientById(patientId) && doctor.checkDoctorById(doctorId)){
            if(checkDoctorAvailability(doctorId,date,connection)) {          //if condition check the date of appointment,doctor.
                String appointmentQuery = "insert into appointment(patient_id, doctor_id, appointment_date) values (?,?,?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, date);

                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0) {
                        System.out.println("Appointment Booked! ");
                    } else {
                        System.out.println("Failed to Booked Appointment!! ");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor not available on this date !!");
            }
        }else {
            System.out.println("Either doctor or patient doesn't exist!!!");
        }
    }

    //check Doctor is available or not in data_Base.
    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection){
        String query = "SELECT COUNT(*) FROM appointment WHERE doctor_id = ? AND appointment_date = ? ";       //check the appointment date of doctor from dataBase.
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);       //preparedStatement used to set data in query field.
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();             //resultSet is used to stored data instance.
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count==0){
                    return true;
                }else {
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
