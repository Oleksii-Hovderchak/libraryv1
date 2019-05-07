package ua.nure.library.domain;

public enum UserRole {

    CLIENT("Client"), ADMIN("Admin");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    /**
     * @return name {@link String}
     */
    public String getName() {
        return name;
    }
}
