package ua.nure.library.converter;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.nure.library.converter.service.ExtendedConversionService;
import ua.nure.library.domain.UserPrincipal;
import ua.nure.library.dto.UserDto;

import java.util.Objects;

@Component
public class UserPrincipalToUserDtoConverter implements Converter<UserPrincipal, UserDto> {

    private ExtendedConversionService conversionService;

    /**
     * @param conversionService conversionService
     */
    public UserPrincipalToUserDtoConverter(@Lazy ExtendedConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public UserDto convert(UserPrincipal source) {
        if (Objects.isNull(source)) {
            return null;
        }
        UserDto userDto = new UserDto();
        String id = source.getUserId();
        userDto.setId(id);
        userDto.setEmail(source.getEmail());
        userDto.setFirstName(source.getFirstName());
        userDto.setLastName(source.getLastName());
        userDto.setRole(source.getRole());
        return userDto;
    }
}
