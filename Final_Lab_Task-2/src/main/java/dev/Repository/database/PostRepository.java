package dev.Repository.database;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
import dev.domain.AvailableForDonation;
import dev.domain.CompletePost;
import dev.domain.Post;

import dev.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            User user = DBUtil.searchUser(userId);

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
    public static Post changePostStatus(Post post) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionManager.getConnection();
        String sql = "UPDATE posts SET status_of_post = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, post.getStatusOfPost().toString());
        preparedStatement.setInt(2, post.getId());

        preparedStatement.execute();
        return searchPost(post.getId());
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
            User user = DBUtil.searchUser(userId);
            Post post = new Post(id, nameOfCreatePost, problemOfPatient, bloodGroup, location, statusOfPost, when, phoneNumber, howManyBagNeed, user);
            postList.add(post);
        }

        return postList;
    }

    public static List<Post> getPostsByStatus() throws SQLException, ClassNotFoundException {
        List<Post> postList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM posts WHERE status_of_post = 'PENDING'";
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
            LocalDate when = LocalDate.parse(resultSet.getString("Event_date"));
            int phoneNumber = resultSet.getInt("phone_number");
            int howManyBagNeed = resultSet.getInt("how_many_bag_need");
            int userId = resultSet.getInt("user_id");
            User user = DBUtil.searchUser(userId);
            Post post = new Post(id, nameOfCreatePost, problemOfPatient, bloodGroup, location, statusOfPost, when, phoneNumber, howManyBagNeed, user);
            postList.add(post);
        }

        return postList;
    }

    public static List<Post> getPostsByStatus2() throws SQLException, ClassNotFoundException {
        List<Post> postList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM posts WHERE status_of_post = 'OPEN'";
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
            LocalDate when = LocalDate.parse(resultSet.getString("Event_date"));
            int phoneNumber = resultSet.getInt("phone_number");
            int howManyBagNeed = resultSet.getInt("how_many_bag_need");
            int userId = resultSet.getInt("user_id");
            User user = DBUtil.searchUser(userId);
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

    public static AvailableForDonation culateAvalableForDonation(int id) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionManager.getConnection();
        // Get the current date and time
        LocalDateTime currentDate = LocalDateTime.now();

//        // Add 4 months to the current date to calculate the next donation date and time
//        LocalDateTime nextDonationDateTime = currentDate.plusMonths(4);
//
//        // Calculate the remaining duration in seconds
//        Duration remainingDuration = Duration.between(currentDate, nextDonationDateTime);
//
//        // Calculate the remaining days, hours, minutes, and seconds
//        long days = remainingDuration.toDays();
//        long hours = (remainingDuration.toHours() % 24);
//        long minutes = (remainingDuration.toMinutes() % 60);
//        long seconds = (remainingDuration.getSeconds() % 60);
//
//        // Create a LocalDateTime object with the remaining duration
//        LocalDateTime remainingDateTime = currentDate
//                .plusDays(days)
//                .plusHours(hours)
//                .plusMinutes(minutes)
//                .plusSeconds(seconds);
        User user = DBUtil.searchUser(id);
        AvailableForDonation a = new AvailableForDonation(currentDate, user);
        String sql = "INSERT INTO calculatedonationtime (UseId, Available_for_donation) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, a.getUser().getId());
        preparedStatement.setString(2, String.valueOf(a.getWhen()));

        preparedStatement.execute();
        return a;
    }


    public static AvailableForDonation culateAvalableForDonationUpdate(int id) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionManager.getConnection();
        // Get the current date and time
        String sql = "SELECT * FROM calculatedonationtime WHERE UseId = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        LocalDateTime availableForDonation = null;
        int Id=0;
        int UseId=0;
        if (resultSet.next()) {
            Id = resultSet.getInt("id");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            availableForDonation = LocalDateTime.parse(resultSet.getString("Available_for_donation"), formatter);
            UseId = resultSet.getInt("UseId");

        }
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime nextDonationDateTime = availableForDonation.plusMonths(4);
        Duration remainingDuration = Duration.between(LocalDateTime.now(), nextDonationDateTime);

        long days = remainingDuration.toDays();
        long hours = (remainingDuration.toHours() % 24);
        long minutes = (remainingDuration.toMinutes() % 60);
        long seconds = (remainingDuration.getSeconds() % 60);

        // Create a LocalDateTime object with the remaining duration
        LocalDateTime remainingDateTime = LocalDateTime.of(0, 1, 1, 0, 0)
                .plusDays(days)
                .plusHours(hours)
                .plusMinutes(minutes)
                .plusSeconds(seconds);
        User user = DBUtil.searchUser(UseId);
        return new AvailableForDonation(remainingDateTime, user);
    }



    public static CompletePost help(Integer PostId, Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionManager.getConnection();
        User helper = DBUtil.searchUser(id);
        Post post=searchPost(PostId);
        String blood = post.getBloodGroup().toString();
        CompletePost.BloodGroup bloodGroup = CompletePost.BloodGroup.valueOf(blood);// Use the BloodGroup enum from the Post class
        CompletePost completeHelpPost = new CompletePost(post.getId(), post.getNameOfCreatePost(), bloodGroup, post.getLocation(), LocalDateTime.now(), post.getPhoneNumber(), post.getUser(), helper);
        String sql = "INSERT INTO complete_help_post (name_of_create_post, blood_group, location, helper_user_id, Helping_Date, phone_number, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, completeHelpPost.getNameOfCreatePost());
        preparedStatement.setString(2, String.valueOf(completeHelpPost.getBloodGroup()));
        preparedStatement.setString(3, completeHelpPost.getLocation());
        preparedStatement.setInt(4, completeHelpPost.getHelpingUser().getId());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(completeHelpPost.getWhen()));
        preparedStatement.setInt(6, completeHelpPost.getPhoneNumber()); // Convert LocalDate to sql.Date
        preparedStatement.setInt(7, completeHelpPost.getUser().getId());
        preparedStatement.execute();
        culateAvalableForDonation(id);
        deleteById(post.getId());
        return completeHelpPost;
    }

    public static int countTotalDonation() throws SQLException, ClassNotFoundException {
        List<CompletePost> TotalDoner = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM complete_help_post";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int Id = resultSet.getInt("id");
            String name_of_create_post = resultSet.getString("name_of_create_post");
            CompletePost.BloodGroup blood_group = CompletePost.BloodGroup.valueOf(resultSet.getString("blood_group"));
            String location = resultSet.getString("location");
            int helperUserId = resultSet.getInt("helper_user_id");
            User HelpUserId = DBUtil.searchUser(helperUserId);
            LocalDateTime when = LocalDateTime.parse(resultSet.getString("Helping_Date"));
            int Phone_Number = resultSet.getInt("phone_number");
            int UserId = resultSet.getInt("user_id");
            User userId = DBUtil.searchUser(UserId);

            CompletePost completePost= new CompletePost(Id, name_of_create_post, blood_group, location, when, Phone_Number, userId, HelpUserId);
            TotalDoner.add(completePost);
        }
        int count=0;
        for (CompletePost p : TotalDoner) {
            count++;
        }
        return count;
    }
}
