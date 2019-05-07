package ua.nure.library.persistence.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.nure.library.domain.UserEntity;
import ua.nure.library.domain.UserRole;

@Component
public class UserSpecification {

    /**
     * Specification searching users by email
     *
     * @param email the email
     * @return user specification
     */
    public Specification<UserEntity> findByEmail(String email) {
        return (root, query, cb) -> SearchSpecification.likeByField(email, cb, root.get(FieldName.EMAIL));
    }

    /**
     * Specification searching users by firstName name
     *
     * @param firstName the firstName of user
     * @return user specification
     */
    public Specification<UserEntity> findByFirstName(String firstName) {
        return (root, query, cb) -> SearchSpecification.likeByField(firstName, cb, root.get(FieldName.FIRST_NAME));
    }

    /**
     * Specification searching users by lastName name
     *
     * @param lastName the lastName of user
     * @return user specification
     */
    public Specification<UserEntity> findByLastName(String lastName) {
        return (root, query, cb) -> SearchSpecification.likeByField(lastName, cb, root.get(FieldName.LAST_NAME));
    }

    /**
     * Specification searching users by address
     *
     * @param address the address of user
     * @return user specification
     */
    public Specification<UserEntity> findByAddress(String address) {
        return (root, query, cb) -> SearchSpecification.equalByField(address, cb, root.get(FieldName.ADDRESS));
    }

    /**
     * Specification searching users by phone
     *
     * @param phone the phone of user
     * @return user specification
     */
    public Specification<UserEntity> findByPhone(String phone) {
        return (root, query, cb) -> SearchSpecification.equalByField(phone, cb, root.get(FieldName.PHONE));
    }

    /**
     * Specification searching users by role
     *
     * @param role the role of user
     * @return user specification
     */
    public Specification<UserEntity> findByRole(UserRole role) {
        return (root, query, cb) -> SearchSpecification.equalByField(role, cb, root.get(FieldName.ROLE));
    }
}
