package ua.nure.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.security.Principal;

/**
 * Represents an authenticated user. The id of the user will be used as the
 * "name of the entity" for javax.security.Principal.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPrincipal implements Principal, Serializable {

    @JsonProperty("id")
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;

    @JsonIgnore
    private transient String accessToken;

    public UserPrincipal() {
    }

    public UserPrincipal(UserEntity user) {
        this.userId = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = user.getUserRole();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UserPrincipal)) return false;

        UserPrincipal that = (UserPrincipal) o;

        return new EqualsBuilder()
                .append(getUserId(), that.getUserId())
                .append(getFirstName(), that.getFirstName())
                .append(getLastName(), getLastName())
                .append(getEmail(), that.getEmail())
                .append(getRole(), that.getRole())
                .append(getAccessToken(), that.getAccessToken())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getUserId())
                .append(getFirstName())
                .append(getLastName())
                .append(getEmail())
                .append(getRole())
                .append(getAccessToken())
                .toHashCode();
    }

    @Override
    public String getName() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(firstName)
                .append(lastName)
                .append(role)
                .toString();
    }
}
