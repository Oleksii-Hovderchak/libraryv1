package ua.nure.library.constant;

public final class RegexConstants {

    public static final String SEARCH_QUERY_FOR_USERS_MAX_35_OR_EMPTY = "[\\w\\d'\\s_@\\.]{2,35}|";
    public static final String USER_ROLES_OR_EMPTY = "(?i)ADMIN|CLIENT|";
    public static final String USER_PHONE_NUMBER =
            "\\(?\\+[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})? ?(\\w{1,10}\\s?\\d{1,6})?";
    public static final String USER_PHONE_NUMBER_OR_EMPTY =
            "\\(?\\+[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})? ?(\\w{1,10}\\s?\\d{1,6})?|";
    public static final String USER_ADDRESS_MIN_2_MAX_35 = "[\\w\\d'\\s_@\\.]{2,35}";
    public static final String USER_ADDRESS_MIN_2_MAX_35_OR_EMPTY = "[\\w\\d'\\s_@\\.]{2,35}|";
}
