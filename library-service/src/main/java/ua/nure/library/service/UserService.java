package ua.nure.library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.nure.library.domain.UserEntity;
import ua.nure.library.domain.UserPrincipal;
import ua.nure.library.domain.UserRole;

import java.util.Optional;

public interface UserService {

    /**
     * Gets all users
     *
     * @param pageable                   for implementing pagination
     * @param firstNameOrLastNameOrEmail is a string for search. Can be firstName, lastName oe email
     * @param userRole                   role of user
     * @param userPhone                  phone of user
     * @param userAddress                address of user
     * @return page of {@link UserEntity}
     */
    Page<UserEntity> getAllUsers(Pageable pageable, String firstNameOrLastNameOrEmail, UserRole userRole, String userPhone, String userAddress);

    /**
     * Gets current user
     *
     * @return {@link UserPrincipal}
     */
    UserPrincipal getCurrentUser();

    /**
     * Gets user by email
     *
     * @param userEmail string param user email
     * @return optional {@link UserEntity}
     */
    Optional<UserEntity> findByEmail(String userEmail);
}
