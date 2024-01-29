package ru.denver7074.autopark.utils;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.BooleanUtils.isTrue;
import static org.apache.commons.lang3.ObjectUtils.anyNotNull;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum Errors {

    E001("Сущность %s с id %s не найдена"),
    E002("Отсутствуют обязательные параметры: %s"),
    E003("Неккоректный ввод email: %s."),
    E004("Машина с vin номером %s уже существует");

    String description;

    public void thr(Boolean value, Object... args) {
        if (isTrue(value)) {
             throw new CustomException(this, anyNotNull(args)
                    ? String.format(this.description, args)
                    : this.description);
        }
    }

    public CustomException thr(Object... args) {
        return new CustomException(this, String.format(this.description, args));
    }

    @Data
    @RequiredArgsConstructor
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    public static class CustomException extends RuntimeException {
        Errors error;
        String message;

        public String getMsg() {
            return StringUtils.defaultString(message, error.getDescription());
        }

        @Override
        public String getMessage() {
            return getMsg();
        }
    }

}
