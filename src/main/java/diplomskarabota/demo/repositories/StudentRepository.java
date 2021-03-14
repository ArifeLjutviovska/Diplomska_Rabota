package diplomskarabota.demo.repositories;


import diplomskarabota.demo.models.StudentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentUser,Long> {
    Optional<StudentUser> findByName(String username);
    Optional<StudentUser> findByEmail(String email);

    Boolean existsByName(String username);

    Boolean existsByEmail(String email);


}
