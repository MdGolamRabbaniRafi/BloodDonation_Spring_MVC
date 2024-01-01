package dev.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "calculatedontiontime")
public class AvailableForDonation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;




    @NotNull(message = "Date and time are required")
    @Column(name = "Available_for_donation")
    private LocalDateTime when;



    @ManyToOne()
    @JoinColumn(name = "UseId")
    private User user;
    public AvailableForDonation(LocalDateTime when, User user) {
        this.when = when;
        this.user = user;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }






    public LocalDateTime getWhen() {
        return when;
    }

    public void setWhen(LocalDateTime when) {
        this.when = when;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
