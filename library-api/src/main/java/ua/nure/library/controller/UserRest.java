package ua.nure.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.library.constant.ErrorMessage;
import ua.nure.library.constant.RegexConstants;
import ua.nure.library.constant.RestParameters;
import ua.nure.library.converter.service.ExtendedConversionService;
import ua.nure.library.dto.ApiResponseDataDto;
import ua.nure.library.dto.PageDto;
import ua.nure.library.dto.UserDto;
import ua.nure.library.service.UserService;
import ua.nure.library.utils.EnumUtils;

import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/users")
public class UserRest {

    @Autowired
    private UserService userService;

    @Autowired
    private ExtendedConversionService conversionService;

    @GetMapping("/all")
    public ApiResponseDataDto<PageDto<UserDto>> getAllUsers(
            @RequestParam(value = RestParameters.SEARCH_QUERY, required = false)
            @Pattern(regexp = RegexConstants.SEARCH_QUERY_FOR_USERS_MAX_35_OR_EMPTY, message = ErrorMessage.SEARCH_QUERY)
                    String firstNameOrLastNameOrEmail,
            @RequestParam(value = RestParameters.USER_ROLE, required = false)
            @Pattern(regexp = RegexConstants.USER_ROLES_OR_EMPTY, message = ErrorMessage.USER_ROLE)
                    String userRole,
            @RequestParam(value = RestParameters.USER_PHONE, required = false)
            @Pattern(regexp = RegexConstants.USER_PHONE_NUMBER_OR_EMPTY, message = ErrorMessage.USER_PHONE)
                    String userPhone,
            @RequestParam(value = RestParameters.USER_ADDRESS, required = false)
            @Pattern(regexp = RegexConstants.USER_ADDRESS_MIN_2_MAX_35_OR_EMPTY, message = ErrorMessage.USER_ADDRESS)
                    String userAddress,
            @SortDefault(sort = "firstName", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ApiResponseDataDto<>(conversionService.convertPage(userService.getAllUsers(pageable, firstNameOrLastNameOrEmail,
                EnumUtils.getUserRole(userRole), userPhone, userAddress), UserDto.class));
    }

    @GetMapping("/my-profile")
    public ApiResponseDataDto<UserDto> getCurrentUser() {
        return new ApiResponseDataDto<>(conversionService.convert(userService.getCurrentUser(), UserDto.class));
    }

}
