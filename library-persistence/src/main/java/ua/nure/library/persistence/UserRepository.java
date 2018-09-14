package ua.nure.library.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.library.domain.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
