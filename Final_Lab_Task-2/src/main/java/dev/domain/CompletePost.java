package dev.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "complete_help_post")
public class CompletePost {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    public enum BloodGroup {
        A_POSITIVE,
        A_NEGATIVE,
        B_POSITIVE,
        B_NEGATIVE,
        AB_POSITIVE,
        AB_NEGATIVE,
        O_POSITIVE,
        O_NEGATIVE;

    }


    @NotNull(message = "Post name is required")
    @Column(name = "name_of_create_post")
    private String nameOfCreatePost;

    @NotNull(message = "Blood group is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroup bloodGroup;

    @NotNull(message = "Location is required")
    @Column(name = "location")
    private String location;

    @NotNull(message = "Date and time are required")
    @Column(name = "Helping_Date")
    private LocalDateTime when;

    @NotNull(message = "Phone number is required")
    @Digits(integer = 10, fraction = 0, message = "Invalid phone number")
    @Column(name = "phone_number")
    private int phoneNumber;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "helper_user_id")
    private User user2;


    // Constructors, Getters, and Setters
    public CompletePost(int id, String nameOfCreatePost, BloodGroup bloodGroup, String location,  LocalDateTime when, int phoneNumber, User user,User user2) {
        this.id = id;
        this.nameOfCreatePost = nameOfCreatePost;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.when = when;
        this.phoneNumber = phoneNumber;
        this.user = user;
        this.user2=user2;
    }
    // Getters and setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfCreatePost() {
        return nameOfCreatePost;
    }

    public void setNameOfCreatePost(String nameOfCreatePost) {
        this.nameOfCreatePost = nameOfCreatePost;
    }



    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public LocalDateTime getWhen() {
        return when;
    }

    public void setWhen(LocalDateTime when) {
        this.when = when;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getHelpingUser() {
        return user2;
    }

    public void setHelpingUser(User user2) {
        this.user2 = user2;
    }
}
