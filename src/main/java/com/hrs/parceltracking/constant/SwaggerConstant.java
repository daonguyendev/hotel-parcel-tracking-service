package com.hrs.parceltracking.constant;

public class SwaggerConstant {
    /* API Info */
    public static final String API_INFO_TITLE = "Parcel Tracking API";
    public static final String API_INFO_DESCRIPTION = "API for tracking parcels in a hotel";
    public static final String API_INFO_VERSION = "1.0";
    public static final String API_INFO_TERM_OF_SERVICE = "Terms of service";
    public static final String API_INFO_CONTACT_NAME = "Dao Nguyen";
    public static final String API_INFO_CONTACT_WEBSITE = "https://everspark.vn";
    public static final String API_INFO_CONTACT_EMAIL = "daonguyen.dev@gmail.com";
    public static final String API_INFO_LICENSE_TITLE = "License of API";
    public static final String API_INFO_LICENSE_URL = "API license URL";

    /* Parcel API  */
    public static final String PARCEL_API_TAGS = "Parcel Management";
    public static final String PARCEL_API_DESCRIPTION = "APIs for managing parcels in the hotel";
    public static final String PARCEL_AS_PICKED_UP_VALUE = "Mark parcel as picked up";
    public static final String PARCEL_AS_PICKED_UP_NOTES = "This API marks a parcel as picked up by a guest.";
    public static final String RECEIVE_A_PARCEL_VALUE = "Receive a parcel";
    public static final String RECEIVE_A_PARCEL_NOTES = "This API allows a receptionist to accept a parcel " +
                                                            "for a checked-in guest.";
    public static final String AVAILABLE_PARCELS_VALUE = "Get available parcels for a guest";
    public static final String AVAILABLE_PARCELS_NOTES = "This API retrieves all available parcels " +
                                                            "for a checked-in guest.";

    /* Guest API */
    public static final String GUEST_API_TAGS = "Guest Management";
    public static final String GUEST_API_DESCRIPTION = "APIs for managing guest check-in and check-out";
    public static final String CHECK_IN_A_GUEST_VALUE = "Check-in a guest";
    public static final String CHECK_IN_A_GUEST_NOTES = "Registers a guest as checked-in.";
    public static final String CHECK_OUT_A_GUEST_VALUE = "Check-out a guest";
    public static final String CHECK_OUT_A_GUEST_NOTES = "Marks a guest as checked out using their ID.";
    public static final String CHECKED_IN_GUESTS_VALUE = "Get checked-in guests with pagination";
    public static final String CHECKED_IN_GUESTS_NOTES = "Retrieves a paginated list of guests " +
                                                            "who are currently checked in.";
}
