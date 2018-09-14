package ua.nure.library.converter;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.nure.library.converter.service.ExtendedConversionService;
import ua.nure.library.domain.UserEntity;
import ua.nure.library.dto.UserDto;

import java.util.Objects;


/**
 * UserEntity to UserDto converter
 */
@Component
public class UserEntityToUserDtoConverter implements Converter<UserEntity, UserDto> {

    private ExtendedConversionService conversionService;

    /**
     * @param conversionService conversionService
     */
    public UserEntityToUserDtoConverter(@Lazy ExtendedConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public UserDto convert(UserEntity source) {
        if (Objects.isNull(source)) {
            return null;
        }
        UserDto userDto = new UserDto();
        String id = source.getId();
        userDto.setId(id);
        userDto.setEmail(source.getEmail());
        userDto.setFirstName(source.getFirstName());
        userDto.setLastName(source.getLastName());
        userDto.setRole(source.getUserRole());
        userDto.setAddress(source.getAddress());
        userDto.setPhone(source.getPhone());
        return userDto;
    }
}
