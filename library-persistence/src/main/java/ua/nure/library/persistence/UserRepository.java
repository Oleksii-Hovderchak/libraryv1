package ua.nure.library.persistence;

import org.springframework.stereotype.Repository;
import ua.nure.library.domain.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, String> {

    /**
     * Fins user by its email case insensitive
     *
     * @param email user email
     * @return Optional with userEntity
     */
    Optional<UserEntity> findByEmailIgnoreCase(String email);
}
