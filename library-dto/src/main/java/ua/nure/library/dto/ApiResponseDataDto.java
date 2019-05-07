package ua.nure.library.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ApiResponseDataDto
 *
 * @param <T> response object type
 */
public class ApiResponseDataDto<T> extends ApiResponseDto {

    private T data;

    public ApiResponseDataDto() {
        //default constructor
    }

    public ApiResponseDataDto(T data) {
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ApiResponseDataDto)) {
            return false;
        }

        ApiResponseDataDto<?> that = (ApiResponseDataDto<?>) o;

        return new EqualsBuilder()
                .append(getData(), that.getData())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getData())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("data", data)
                .toString();
    }
}
