package com.hrs.parceltracking.constant;

public class DataSourceConstant {
    public static final String DATA_SOURCE_PREFIX = "jdbc:%s://%s:%s/%s%s%s";
    public static final String CREATE_DB_IF_NOT_EXIST = "?createDatabaseIfNotExist=";
    public static final boolean IS_CREATE_DB = true;
    public static final String DB_NAME_IS_REQUIRED = "Database name is required";
}
