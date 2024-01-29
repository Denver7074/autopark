package ru.denver7074.autopark.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"isFinish", "code", "details", "data"})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NegativeResponse<T> extends Response<T> {
    T data;
    String code;
    Object details;

    public NegativeResponse(String code, Object details, T data) {
        super(Boolean.FALSE);
        this.code = code;
        this.details = details;
        this.data = data;
    }
}
