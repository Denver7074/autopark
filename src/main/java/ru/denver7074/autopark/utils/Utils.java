package ru.denver7074.autopark.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.LOWER_UNDERSCORE;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isTrue;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static ru.denver7074.autopark.utils.Constants.LIST_FORMAT_DATE;

@Slf4j
public class Utils {

    @FunctionalInterface
    public interface Supplier<T, E extends Throwable> {
        T get() throws E;
    }

    public static <E> E safeGet(Supplier<E, ? extends Exception> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate date(Object o) {
        if (isNull(o)) return null;
        String date = String.valueOf(o);
        LocalDate parse = null;
        for (String format : LIST_FORMAT_DATE) {
            parse = safeGet(() -> DateTimeFormatter.ofPattern(format).parse(date, LocalDate::from));
            if (isNotEmpty(parse)) break;
        }
        return parse;
    }
}
