package ua.nure.library.utils;

import ua.nure.library.domain.UserRole;

import java.util.Arrays;

public class EnumUtils {

    public static UserRole getUserRole(String userRole) {
        return Arrays.stream(UserRole.values())
                .filter(testSessionStatusEntry -> testSessionStatusEntry.getName().equalsIgnoreCase(userRole))
                .findFirst()
                .orElse(null);
    }

}
