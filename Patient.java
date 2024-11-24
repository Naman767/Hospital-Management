package Hospital.Management.System;
import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.println("Enter the Patient Name: ");
        String name = scanner.next();

        System.out.println("Enter the Patient Age: ");
        int age = scanner.nextInt();

        System.out.println("Enter Patient Gender : ");
        String gender =  scanner.next();

        try {
                String query = "INSERT INTO patients(name, age, gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement  = connection.prepareStatement(query);     //Used to connection with statement.
            preparedStatement.setString(1,name);            //set the values of variables.
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affectedRow = preparedStatement.executeUpdate();        //showing affected line or count the No. of row executed.
            if(affectedRow>0){                                          //IF TRUE.
                System.out.println("Patient Added Successfully !! ");
            }else{                                                      //IF FALSE.
                System.out.println("Failed to Add Patient !!");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void viewPatients(){
        String query = "select *from patients";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);   //Connect the prepareStatement to query.
            ResultSet resultset = preparedStatement.executeQuery();         //set the value into result set by instance.
            System.out.println("Patients: ");               //Patients table
            System.out.println("+---------------+---------------------+-------------+-------------+");  //First field.
            System.out.println("| Patient Id    | Name                | Age         | Gender      |");  //info column.
            System.out.println("+---------------+---------------------+-------------+-------------+");  //last field.
            while (resultset.next()){                                   //used to fetch the data into the text field
                int id  = resultset.getInt("id");            //Fetch the ID data.
                String name = resultset.getString("name");   //Fetch the name data string form.
                int age = resultset.getInt("age");           //Fetch the age data.
                String gender = resultset.getString("gender");//Fetch the gender data.

                //Print data into field table form.
                System.out.printf("| %-14s | %-20s | %-12s | %-12s |\n",id,name,age,gender);
                System.out.println("+---------------+---------------------+-------------+-------------+");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
        public boolean checkPatientById(int id){
        String query = "select *from patients where id = ?";        //SQL query fetch data from DataBase.

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);   //connect query data with prepareStatement.
            preparedStatement.setInt(1,id);             //id data fetch.
            ResultSet resultSet = preparedStatement.executeQuery();     //store data into resultSet interface
            if(resultSet.next()){           //check the patient is existed or not in database.
                return true;                //IF Yes then True return.
            }else {
                return false;               //otherwise False return.
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;           //ByDefault return False otherwise execute program.
    }
}
