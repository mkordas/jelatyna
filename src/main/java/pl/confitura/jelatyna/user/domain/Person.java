package pl.confitura.jelatyna.user.domain;

import static java.time.LocalDateTime.*;
import static java.util.Optional.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private String token;

    @OneToOne(cascade = CascadeType.ALL)
    private Registration registration;

    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;

    public void copyFrom(Person person) {
        email = person.email;
        firstName = person.firstName;
        lastName = person.lastName;
    }

    public void arrived() {
        registration.setArrivalDate(now());
    }

    public boolean isArrived() {
        return ofNullable(registration)
            .orElse(new Registration()).getArrivalDate() != null;
    }

    public void reject() {
        registration.setArrivalDate(null);
    }

    public void stamp() {
        registration.setStampDate(now());
    }

    public void unstamp() {
        registration.setStampDate(null);
    }

    public boolean isStamped() {
        return registration.getStampDate() != null;
    }

}
