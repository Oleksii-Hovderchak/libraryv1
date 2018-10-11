package ua.nure.library.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.nure.library.domain.UserEntity;
import ua.nure.library.domain.UserPrincipal;
import ua.nure.library.domain.UserRole;
import ua.nure.library.persistence.UserRepository;
import ua.nure.library.persistence.specification.SpecificationUtils;
import ua.nure.library.persistence.specification.UserSpecification;
import ua.nure.library.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserSpecification userSpecification;

    /**
     * @param userRepository    userRepository
     * @param userSpecification userSpecification
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserSpecification userSpecification) {
        this.userRepository = userRepository;
        this.userSpecification = userSpecification;
    }

    @Override
    public Page<UserEntity> getAllUsers(Pageable pageable,
                                        String firstNameOrLastNameOrEmail,
                                        UserRole userRole,
                                        String userPhone,
                                        String userAddress) {

        Specification<UserEntity> nameUserSpecification = Specifications
                .where(userSpecification.findByEmail(firstNameOrLastNameOrEmail))
                .or(userSpecification.findByFirstName(firstNameOrLastNameOrEmail))
                .or(userSpecification.findByLastName(firstNameOrLastNameOrEmail));

        Specification<UserEntity> phoneUserSpecification = SpecificationUtils.conjunction();
        if (StringUtils.isNotBlank(userPhone)) {
            phoneUserSpecification = Specifications.where(userSpecification.findByPhone(userPhone));
        }

        Specification<UserEntity> addressUserSpecification = SpecificationUtils.conjunction();
        if (StringUtils.isNotBlank(userAddress)) {
            addressUserSpecification = Specifications.where(userSpecification.findByAddress(userAddress));
        }

        Specification<UserEntity> roleUserSpecification = Specifications.where(userSpecification.findByRole(userRole));

        Specification<UserEntity> userEntitySpecification = Specifications.where(nameUserSpecification)
                .and(addressUserSpecification).and(phoneUserSpecification).and(roleUserSpecification);

        return userRepository.findAll(userEntitySpecification, pageable);
    }

    @Override
    public UserPrincipal getCurrentUser() {
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public Optional<UserEntity> findByEmail(String userEmail) {
        return userRepository.findByEmailIgnoreCase(userEmail);
    }
}
