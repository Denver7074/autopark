package ru.denver7074.autopark.utils;


import java.util.List;
import java.util.regex.Pattern;

public final class Constants {

    public final static List<String> LIST_FORMAT_DATE = List.of("yyyy-MM-dd", "dd.MM.yyyy", "yyyy.MM.dd", "MM/dd/yyyy");
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

}
