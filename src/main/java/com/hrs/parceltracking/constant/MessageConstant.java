package com.hrs.parceltracking.constant;

public class MessageConstant {
    /* Validation Messages */
    public static final String INVALID_PASSWORD_FORMAT = "Invalid password format";
    public static final String DB_NAME_IS_REQUIRED = "Database name is required";
    public static final String TRACKING_NUMBER_IS_REQUIRED = "Tracking number is required";
    public static final String TRACKING_NUMBER_MAX_255_CHARS = "Tracking number must be at most 255 characters";
    public static final String RECIPIENT_NAME_IS_REQUIRED = "Recipient name is required";
    public static final String RECIPIENT_NAME_MAX_255_CHARS = "Recipient name must be at most 255 characters";
    public static final String ROOM_NUMBER_IS_REQUIRED = "Room number is required";
    public static final String ROOM_NUMBER_MAX_45_CHARS = "Room number must be at most 45 characters";

    /* Guest Messages */
    public static final String GUEST_CHECKIN_SUCCESS = "Guest checked in successfully!";
    public static final String GUEST_CHECKOUT_SUCCESS = "Guest checked out successfully!";
    public static final String GUEST_ALREADY_CHECKED_IN = "Guest is already checked in!";
    public static final String GUEST_NOT_FOUND = "Guest not found!";
    public static final String INVALID_GUEST_INFO = "Guest not found or has already checkout or wrong room number!";
    public static final String GUEST_ALREADY_CHECKED_OUT = "Guest has already checked out!";
    public static final String VALIDATION_ERROR = "Validation error!";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String CHECKED_IN_GUESTS_RETRIEVED_SUCCESS = "Checked-in guests retrieved successfully";

    /* Parcel Messages */
    public static final String PARCEL_NOT_FOUND = "Parcel not found. Please check tracking number again!";
    public static final String PARCEL_ALREADY_PICKED_UP = "Parcel has already been picked up.";
    public static final String PARCEL_MARKED_AS_PICKED_UP = "Parcel marked as picked up.";
    public static final String PARCEL_RECEIVED_SUCCESS = "Parcel received successfully!";
    public static final String SUCCESS_RETRIEVED_AVAILABLE_PARCELS = "Successfully retrieved available parcels";
}
