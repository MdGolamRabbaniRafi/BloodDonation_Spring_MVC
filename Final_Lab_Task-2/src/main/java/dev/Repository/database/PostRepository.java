package dev.Repository.database;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
import dev.domain.Post;

import dev.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Repository
public class PostRepository {

    public static Post searchPost(int id) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM posts WHERE Id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int postId = resultSet.getInt("id");
            String nameOfCreatePost = resultSet.getString("name_of_create_post");
            String problemOfPatient = resultSet.getString("problem_of_patient");
            Post.BloodGroup bloodGroup = Post.BloodGroup.valueOf(resultSet.getString("blood_group"));
            String location = resultSet.getString("location");
            String statusValue = resultSet.getString("status_of_post");
            Post.StatusOfPost statusOfPost = Post.StatusOfPost.valueOf(statusValue);
         //   Post.StatusOfPost statusOfPost = Post.StatusOfPost.valueOf(resultSet.getString("status_of_post"));
            LocalDate when = LocalDate.parse(resultSet.getString("Event_date"));
            int phoneNumber = resultSet.getInt("phone_number");
            int howManyBagNeed = resultSet.getInt("how_many_bag_need");
            // Assuming user_id is an integer and corresponds to an existing user.
            int userId = resultSet.getInt("user_id");
            User user =DBUtil.searchUser(userId);

            return new Post(postId, nameOfCreatePost, problemOfPatient, bloodGroup, location, statusOfPost, when, phoneNumber, howManyBagNeed, user);
        } else {
            return null;
        }
    }
    public static Post create(Post post) throws SQLException, ClassNotFoundException {
        post.setStatusOfPost(Post.StatusOfPost.valueOf("PENDING"));
        Connection connection = ConnectionManager.getConnection();
        // Updated SQL with renamed `when` to `event_date`
        String sql = "INSERT INTO posts (name_of_create_post, problem_of_patient, blood_group, location, status_of_post, Event_date, phone_number, how_many_bag_need, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, post.getNameOfCreatePost());
        preparedStatement.setString(2, post.getProblemOfPatient());
        preparedStatement.setString(3, String.valueOf(post.getBloodGroup()));
        preparedStatement.setString(4, post.getLocation());
        preparedStatement.setString(5, "PENDING"); // Use Enum value as String
        preparedStatement.setDate(6, Date.valueOf(post.getWhen())); // Convert LocalDate to sql.Date
        preparedStatement.setInt(7, post.getPhoneNumber());
        preparedStatement.setInt(8, post.getHowManyBagNeed());

        // Make sure user is not null or handle it properly
        if (post.getUser() != null && post.getUser().getId() != 0) {
            preparedStatement.setInt(9, post.getUser().getId());
        } else {
            // Handle case when user is null - maybe throw an exception or log an error
        }

        preparedStatement.execute();
        return post;
    }


    public static Post update(int id, Post post) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionManager.getConnection();
        String sql = "UPDATE posts SET name_of_create_post = ?, problem_of_patient = ?, blood_group = ?, location = ?, status_of_post = ?, Event_date = ?, phone_number = ?, how_many_bag_need = ?, user_id = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, post.getNameOfCreatePost());
        preparedStatement.setString(2, post.getProblemOfPatient());
        preparedStatement.setString(3, String.valueOf(post.getBloodGroup()));
        preparedStatement.setString(4, post.getLocation());
        preparedStatement.setString(5, String.valueOf(post.getStatusOfPost()));
        preparedStatement.setDate(6, Date.valueOf(post.getWhen()));
        preparedStatement.setInt(7, post.getPhoneNumber());
        preparedStatement.setInt(8, post.getHowManyBagNeed());
        preparedStatement.setInt(9, post.getUser().getId());
        preparedStatement.setInt(10, id);


        preparedStatement.execute();
        return post;
    }

    // Check if the input newStatus is valid before setting it
    public static Post changePostStatus(int id, String newStatus) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionManager.getConnection();
        Post post = searchPost(id);

        // Check if newStatus is a valid enum constant before setting it
        try {
            post.setStatusOfPost(Post.StatusOfPost.valueOf(newStatus));
        } catch (IllegalArgumentException e) {
            // Handle the case where newStatus is not a valid enum constant
            throw new IllegalArgumentException("Invalid statusOfPost value: " + newStatus);
        }

        String sql = "UPDATE posts SET status_of_post = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(post.getStatusOfPost()));
        preparedStatement.setInt(2, id);

        preparedStatement.execute();
        return searchPost(id);
    }


    public static void deleteById(int post) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM posts WHERE ID = ? ");
        preparedStatement.setInt(1, post);
        preparedStatement.execute();
    }

    public static List<Post> getAllPosts() throws SQLException, ClassNotFoundException {
        List<Post> postList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM posts";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nameOfCreatePost = resultSet.getString("name_of_create_post");
            String problemOfPatient = resultSet.getString("problem_of_patient");
            String bloodGroupStr = resultSet.getString("blood_group");
            Post.BloodGroup bloodGroup = Post.BloodGroup.valueOf(bloodGroupStr); // Assuming blood group is stored as a string
            String statusOfPostStr = resultSet.getString("status_of_post");
            Post.StatusOfPost statusOfPost = Post.StatusOfPost.valueOf(statusOfPostStr); // Assuming status is stored as a string
            String location = resultSet.getString("location");
            LocalDate when = LocalDate.parse(resultSet.getString("when"));
            int phoneNumber = resultSet.getInt("phone_number");
            int howManyBagNeed = resultSet.getInt("how_many_bag_need");
            int userId = resultSet.getInt("user_id");
            User user =DBUtil.searchUser(userId);
            Post post = new Post(id, nameOfCreatePost, problemOfPatient, bloodGroup, location, statusOfPost, when, phoneNumber, howManyBagNeed, user);
            postList.add(post);
        }

        return postList;
    }

    public static List<Post> getPostsByStatus() throws SQLException, ClassNotFoundException {
        List<Post> postList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM posts WHERE status_of_post = 'Pending'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nameOfCreatePost = resultSet.getString("name_of_create_post");
            String problemOfPatient = resultSet.getString("problem_of_patient");
            String bloodGroupStr = resultSet.getString("blood_group");
            Post.BloodGroup bloodGroup = Post.BloodGroup.valueOf(bloodGroupStr); // Assuming blood group is stored as a string
            String statusOfPostStr = resultSet.getString("status_of_post");
            Post.StatusOfPost statusOfPost = Post.StatusOfPost.valueOf(statusOfPostStr); // Assuming status is stored as a string
            String location = resultSet.getString("location");
            LocalDate when = LocalDate.parse(resultSet.getString("when"));
            int phoneNumber = resultSet.getInt("phone_number");
            int howManyBagNeed = resultSet.getInt("how_many_bag_need");
            int userId = resultSet.getInt("user_id");
            User user =DBUtil.searchUser(userId);
            Post post = new Post(id, nameOfCreatePost, problemOfPatient, bloodGroup, location, statusOfPost, when, phoneNumber, howManyBagNeed, user);
            postList.add(post);
        }

        return postList;
    }
    public static List<Post> getPostsByStatus2() throws SQLException, ClassNotFoundException {
        List<Post> postList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM posts WHERE status_of_post = 'Open'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nameOfCreatePost = resultSet.getString("name_of_create_post");
            String problemOfPatient = resultSet.getString("problem_of_patient");
            String bloodGroupStr = resultSet.getString("blood_group");
            Post.BloodGroup bloodGroup = Post.BloodGroup.valueOf(bloodGroupStr); // Assuming blood group is stored as a string
            String statusOfPostStr = resultSet.getString("status_of_post");
            Post.StatusOfPost statusOfPost = Post.StatusOfPost.valueOf(statusOfPostStr); // Assuming status is stored as a string
            String location = resultSet.getString("location");
            LocalDate when = LocalDate.parse(resultSet.getString("when"));
            int phoneNumber = resultSet.getInt("phone_number");
            int howManyBagNeed = resultSet.getInt("how_many_bag_need");
            int userId = resultSet.getInt("user_id");
            User user =DBUtil.searchUser(userId);
            Post post = new Post(id, nameOfCreatePost, problemOfPatient, bloodGroup, location, statusOfPost, when, phoneNumber, howManyBagNeed, user);
            postList.add(post);
        }

        return postList;
    }


    public static int count() throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT COUNT(*) AS total FROM posts";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("total");
        } else {
            return 0;
        }
    }


}
