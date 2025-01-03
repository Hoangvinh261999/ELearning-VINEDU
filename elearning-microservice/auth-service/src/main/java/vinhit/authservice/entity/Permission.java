package vinhit.authservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user_id;
    @ManyToOne
    @JoinColumn(name = "role_id")
    @Enumerated(EnumType.STRING)
    Role role_id;
    LocalDate createAt;
    LocalDate updateAt;
    String description;

}
