package nl.jkspecs.repository;

import nl.jkspecs.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<List<User>> findByMobileNumber(String mobileNumber);
    Optional <List<User>> findByBirthdayAndLastName(LocalDate birthdayDate, String lastName);
    Optional <List<User>> findByLastName(String lastName);
}
