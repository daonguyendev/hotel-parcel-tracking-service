package com.hrs.parceltracking.constant;

public class RegexConstant {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9].[a-zA-Z0-9\\._%\\+\\-]{0,63}@[a-zA-Z0-9\\.\\-]+\\.[a-zA-Z]{2,30}$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])\\S{6,}$";
}
