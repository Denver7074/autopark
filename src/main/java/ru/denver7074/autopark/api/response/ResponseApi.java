package ru.denver7074.autopark.api.response;

import org.springframework.data.domain.Page;

import java.util.Optional;

public class ResponseApi {

    public static <T> PositiveResponse<T> positiveResponse(T data) {
        return new PositiveResponse<>(data);
    }

    public static PositiveResponse<Object> emptyPositiveResponse() {
        return new PositiveResponse<>(Optional.empty());
    }

    public static NegativeResponse<Object> negativeResponse(String code, String message, Object data) {
        return new NegativeResponse<>(code, message, data);
    }

}
