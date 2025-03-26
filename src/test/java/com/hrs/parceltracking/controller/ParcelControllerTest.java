package com.hrs.parceltracking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.parceltracking.constant.ApiConstant;
import com.hrs.parceltracking.constant.MessageConstant;
import com.hrs.parceltracking.dto.request.ParcelRequest;
import com.hrs.parceltracking.entity.Guest;
import com.hrs.parceltracking.entity.Parcel;
import com.hrs.parceltracking.service.ParcelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ParcelControllerTest {

    @Mock
    private ParcelService parcelService;

    @InjectMocks
    private ParcelController parcelController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Convert object -> JSON

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(parcelController).build();
    }

    @Test
    void receiveParcel_ShouldReturnOkStatus() throws Exception {
        // Arrange
        ParcelRequest request = new ParcelRequest("PKG001", "John Doe", "101");

        doNothing().when(parcelService).receiveParcel(any(ParcelRequest.class));

        // Act & Assert
        mockMvc.perform(post(ApiConstant.PARCEL_API_PREFIX + ApiConstant.RECEIVE_PARCEL_API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value(MessageConstant.PARCEL_RECEIVED_SUCCESS));

        verify(parcelService, times(1)).receiveParcel(any(ParcelRequest.class));
    }

    @Test
    void getParcelsForGuest_ShouldReturnParcelList() throws Exception {
        // Arrange
        Long guestId = 1L;
        ParcelRequest request = new ParcelRequest("PKG001", "John Doe", "101");
        Guest guest = new Guest(guestId, "John Doe", "101", true);
        Parcel parcel = new Parcel(1L, "PKG001", "John Doe", guest, false);
        List<Parcel> parcelList = Collections.singletonList(parcel);
        Page<Parcel> parcelPage = new PageImpl<>(parcelList, PageRequest.of(0, 10), 1);

        when(parcelService.getParcelsForGuest(anyLong(), anyInt(), anyInt(), anyString())).thenReturn(parcelPage);

        // Act & Assert
        mockMvc.perform(get(ApiConstant.PARCEL_API_PREFIX + ApiConstant.AVAILABLE_PARCELS_API_ENDPOINT, guestId)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value(MessageConstant.SUCCESS_RETRIEVED_AVAILABLE_PARCELS));

        verify(parcelService, times(1)).getParcelsForGuest(anyLong(), anyInt(), anyInt(), anyString());
    }

    @Test
    void markParcelAsPickedUp_ShouldReturnOkStatus() throws Exception {
        // Arrange
        Long parcelId = 1L;
        doNothing().when(parcelService).markParcelAsPickedUp(parcelId);

        // Act & Assert
        mockMvc.perform(post(ApiConstant.PARCEL_API_PREFIX + ApiConstant.MARK_PARCEL_PICKED_UP_API_ENDPOINT, parcelId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value(MessageConstant.PARCEL_MARKED_AS_PICKED_UP));

        verify(parcelService, times(1)).markParcelAsPickedUp(parcelId);
    }
}

