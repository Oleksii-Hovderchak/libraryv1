package ua.nure.library.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

/**
 * ApiResponseErrorDto
 */
public class ApiResponseErrorDto extends ApiResponseDto {

    private List<ResponseError> errors;

    public ApiResponseErrorDto(String message) {
        this.errors = Collections.singletonList(new ResponseError(message));
    }

    public ApiResponseErrorDto(List<ResponseError> errors) {
        this.errors = errors;
    }

    public List<ResponseError> getErrors() {
        return errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiResponseErrorDto that = (ApiResponseErrorDto) o;
        return new EqualsBuilder()
                .append(getErrors(), that.getErrors())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getErrors())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("errors", errors)
                .appendSuper(super.toString())
                .toString();
    }

    /**
     * ResponseError
     *
     * @param <T> class type
     */
    public static class ResponseError<T> {
        private final String field;
        private final String msg;
        private final T wrongValue;

        public ResponseError(String field, String msg, T wrongValue) {
            this.field = field;
            this.msg = msg;
            this.wrongValue = wrongValue;
        }

        public ResponseError(String msg) {
            this.msg = msg;
            field = null;
            wrongValue = null;
        }

        public ResponseError(String msg, T wrongValue) {
            this.msg = msg;
            this.wrongValue = wrongValue;
            field = null;
        }

        public String getField() {
            return field;
        }

        public String getMsg() {
            return msg;
        }

        public T getWrongValue() {
            return wrongValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ResponseError<?> that = (ResponseError<?>) o;
            return new EqualsBuilder()
                    .append(getField(), that.getField())
                    .append(getMsg(), that.getMsg())
                    .append(getWrongValue(), that.getWrongValue())
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(getField())
                    .append(getMsg())
                    .append(getWrongValue())
                    .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("field", field)
                    .append("msg", msg)
                    .append("wrongValue", wrongValue)
                    .toString();
        }
    }
}
