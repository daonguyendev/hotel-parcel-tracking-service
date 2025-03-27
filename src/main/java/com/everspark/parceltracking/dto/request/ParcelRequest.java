package com.everspark.parceltracking.dto.request;

import com.everspark.parceltracking.constant.MessageConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParcelRequest {

    @NotBlank(message = MessageConstant.TRACKING_NUMBER_IS_REQUIRED)
    @Size(max = 255, message = MessageConstant.TRACKING_NUMBER_MAX_255_CHARS)
    private String trackingNumber;

    @NotBlank(message = MessageConstant.RECIPIENT_NAME_IS_REQUIRED)
    @Size(max = 255, message = MessageConstant.RECIPIENT_NAME_MAX_255_CHARS)
    private String recipientName;

    @NotBlank(message = MessageConstant.ROOM_NUMBER_IS_REQUIRED)
    @Size(max = 45, message = MessageConstant.ROOM_NUMBER_MAX_45_CHARS)
    private String roomNumber;
}
