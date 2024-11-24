package Hospital.Management.System;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection){
        this.connection = connection;

    }

    public void viewDoctor(){
        String query = "select *from doctors";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);   //Connect the prepareStatement to query.
            ResultSet resultset = preparedStatement.executeQuery();         //set the value into result set by instance.
            System.out.println("Doctor: ");               //Patients table
            System.out.println("+---------------+---------------------+--------------------+");  //First field.
            System.out.println("| Doctor Id     | Name                | Specialization     |");  //info column.
            System.out.println("+---------------+---------------------+--------------------+");  //last field.
            while (resultset.next()){                                   //used to fetch the data into the text field
                int id  = resultset.getInt("id");            //Fetch the ID data.
                String name = resultset.getString("name");   //Fetch the name data string form.
                String specialization = resultset.getString("specialization");

                //Print data into field table form.
                System.out.printf("| %-13s | %-19s | %-18s |\n", id, name, specialization);           //Changed into printf() to print();
                System.out.println("+---------------+---------------------+--------------------+");

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean checkDoctorById(int id){
        String query = "select *from doctors where id = ?";        //SQL query fetch data from DataBase.

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

