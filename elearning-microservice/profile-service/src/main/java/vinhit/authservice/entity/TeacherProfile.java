package vinhit.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import vinhit.authservice.enums.Role_Profile;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TeacherProfile extends UserProfile{
    private int year_of_experiment;
    private LocalDate startTeachingDate;
    private LocalDate contractDuration;
    @Enumerated(EnumType.STRING)
    private Role_Profile contractType;
}
