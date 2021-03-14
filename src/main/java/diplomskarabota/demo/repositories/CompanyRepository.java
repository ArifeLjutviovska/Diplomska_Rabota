package diplomskarabota.demo.repositories;


import diplomskarabota.demo.models.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyUser,Long> {
    Optional<CompanyUser> findByName(String username);
    Optional<CompanyUser> findByEmail(String email);

    Boolean existsByName(String username);

    Boolean existsByEmail(String email);




}
