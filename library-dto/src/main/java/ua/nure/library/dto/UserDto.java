package ua.nure.library.dto;

import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import ua.nure.library.domain.UserRole;

public class UserDto {

    @NotNull
//    @Pattern(regexp = "")
    private String id;

    @NotNull
//    @Pattern(regexp = "")
    private String firstName;

    @NotNull
//    @Pattern(regexp = "")
    private String lastName;

    @NotNull
//    @Pattern(regexp = "")
    private String email;

    @NotNull
//    @Pattern(regexp = "")
    private UserRole role;

    @NotNull
//    @Pattern(regexp = "")
    private String address;

    //    @Pattern(regexp = "")
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UserDto)) return false;

        UserDto userDto = (UserDto) o;

        return new EqualsBuilder()
                .append(getId(), userDto.getId())
                .append(getFirstName(), userDto.getFirstName())
                .append(getLastName(), userDto.getLastName())
                .append(getEmail(), userDto.getEmail())
                .append(getRole(), userDto.getRole())
                .append(getAddress(), userDto.getAddress())
                .append(getPhone(), userDto.getPhone())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getFirstName())
                .append(getLastName())
                .append(getEmail())
                .append(getRole())
                .append(getAddress())
                .append(getPhone())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("email", email)
                .append("role", role)
                .append("address", address)
                .append("phone", phone)
                .toString();
    }
}
