package dev.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "posts")
public class Post {


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
    public enum StatusOfPost {
        OPEN,
        CLOSED,
        PENDING;

    }


    @NotNull(message = "Post name is required")
    @Column(name = "name_of_create_post")
    private String nameOfCreatePost;

    @NotNull(message = "Problem description is required")
    @Column(name = "problem_of_patient")
    private String problemOfPatient;

    @NotNull(message = "Blood group is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroup bloodGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_of_post")
    private StatusOfPost statusOfPost;

    @NotNull(message = "Location is required")
    @Column(name = "location")
    private String location;

    @NotNull(message = "Date and time are required")
    @Column(name = "Event_date")
    private LocalDate when;

    @NotNull(message = "Phone number is required")
    @Digits(integer = 10, fraction = 0, message = "Invalid phone number")
    @Column(name = "phone_number")
    private int phoneNumber;

    @Min(value = 1, message = "At least one bag is needed")
    @Column(name = "how_many_bag_need")
    private int howManyBagNeed;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors, Getters, and Setters
    public Post() {
    }

    public Post(int id, String nameOfCreatePost, String problemOfPatient, BloodGroup bloodGroup, String location, StatusOfPost statusOfPost, LocalDate when, int phoneNumber, int howManyBagNeed, User user) {
        this.id = id;
        this.nameOfCreatePost = nameOfCreatePost;
        this.problemOfPatient = problemOfPatient;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.statusOfPost = statusOfPost;
        this.when = when;
        this.phoneNumber = phoneNumber;
        this.howManyBagNeed = howManyBagNeed;
        this.user = user;
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

    public String getProblemOfPatient() {
        return problemOfPatient;
    }

    public void setProblemOfPatient(String problemOfPatient) {
        this.problemOfPatient = problemOfPatient;
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

    public StatusOfPost getStatusOfPost() {
        return statusOfPost;
    }

    public void setStatusOfPost(StatusOfPost statusOfPost) {
        this.statusOfPost = statusOfPost;
    }

    public LocalDate getWhen() {
        return when;
    }

    public void setWhen(LocalDate when) {
        this.when = when;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getHowManyBagNeed() {
        return howManyBagNeed;
    }

    public void setHowManyBagNeed(int howManyBagNeed) {
        this.howManyBagNeed = howManyBagNeed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
