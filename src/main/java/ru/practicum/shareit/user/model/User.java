package ru.practicum.shareit.user.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "user_name", nullable = false)
    String name;

    @Column(name = "user_email", unique = true)
    String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}