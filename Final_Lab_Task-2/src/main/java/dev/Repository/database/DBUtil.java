package dev.Repository.database;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import dev.domain.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Repository
public class DBUtil {
    public DBUtil() {
    }

    public static User searchUser(int id) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM users WHERE Id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int Id = resultSet.getInt("Id");
            String fullName = resultSet.getString("FullName");
            String Email = resultSet.getString("Email");
            LocalDate DOB = LocalDate.parse(resultSet.getString("DOB"));
            User.Gender Gender = User.Gender.valueOf(resultSet.getString("Gender"));
            String Country = resultSet.getString("Country");
            String Password = resultSet.getString("Password");

            User user = new User(fullName,Email,Id,DOB,Gender,Country,Password);
            return user;
        } else {
            return null;
        }
    }
    public static void create(User user) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (Id, FullName, Email, DOB, Gender, Country, enabled, Password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, user.getId());

        preparedStatement.setString(2, user.getFullname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setDate(4, Date.valueOf(user.getDob()));
        preparedStatement.setString(5, String.valueOf(user.getGender()));
        preparedStatement.setString(6, user.getCountry());
        preparedStatement.setInt(7, 1);
        preparedStatement.setString(8, user.getPassword());


        preparedStatement.execute();
    }
    public static void update(User user) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET FullName = ?, Email = ?, DOB = ?, Gender = ?, Country = ? WHERE Id = ?");
        preparedStatement.setString(1, user.getFullname());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setDate(3, Date.valueOf(user.getDob()));
        preparedStatement.setString(4, user.getGender().toString());
        preparedStatement.setString(5, user.getCountry());
        preparedStatement.setInt(6, user.getId());

        preparedStatement.execute();
    }

    public static void delete(int user) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE ID = ? ");
        preparedStatement.setInt(1, user);
        preparedStatement.execute();
    }

    public static List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        List<User> userList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM users";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int Id = resultSet.getInt("Id");
            String fullName = resultSet.getString("FullName");
            String Email = resultSet.getString("Email");
            LocalDate DOB = LocalDate.parse(resultSet.getString("DOB"));
            User.Gender Gender = User.Gender.valueOf(resultSet.getString("Gender"));
            String Country = resultSet.getString("Country");
            String Password = resultSet.getString("Password");

            User user = new User(fullName, Email, Id, DOB, Gender, Country,Password);
            userList.add(user);
        }

        return userList;
    }


    public static int countAlluser() throws SQLException, ClassNotFoundException {
        List<User> userList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM users";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int Id = resultSet.getInt("Id");
            String fullName = resultSet.getString("FullName");
            String Email = resultSet.getString("Email");
            LocalDate DOB = LocalDate.parse(resultSet.getString("DOB"));
            User.Gender Gender = User.Gender.valueOf(resultSet.getString("Gender"));
            String Country = resultSet.getString("Country");
            String Password = resultSet.getString("Password");

            User user = new User(fullName, Email, Id, DOB, Gender, Country, Password);
            userList.add(user);
        }
        int count=0;
        for (User user : userList) {
            count++;
        }
        return count;
    }
}
