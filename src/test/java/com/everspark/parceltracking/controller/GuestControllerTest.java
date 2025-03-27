package com.everspark.parceltracking.controller;

import com.everspark.parceltracking.constant.ApiConstant;
import com.everspark.parceltracking.constant.MessageConstant;
import com.everspark.parceltracking.entity.Guest;
import com.everspark.parceltracking.service.GuestService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
public class GuestControllerTest {

    @Mock
    private GuestService guestService;

    @InjectMocks
    private GuestController guestController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(guestController).build();
    }

    @Test
    void checkIn_ShouldReturnCreatedStatus() throws Exception {
        // Arrange
        Guest guest = new Guest(1L, "John Doe", "101", true);
        Guest checkedInGuest = new Guest(1L, "John Doe (Checked In)", "101", true);

        when(guestService.checkIn(any(Guest.class))).thenReturn(checkedInGuest);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post(ApiConstant.GUEST_API_PREFIX + ApiConstant.CHECK_IN_API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(guest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.message").value(MessageConstant.GUEST_CHECKIN_SUCCESS))
                .andExpect(jsonPath("$.data.name").value("John Doe (Checked In)"));

        verify(guestService, times(1)).checkIn(any(Guest.class));
    }

    @Test
    void checkOut_ShouldReturnOkStatus() throws Exception {
        // Arrange
        Long guestId = 1L;
        doNothing().when(guestService).checkOut(guestId);

        // Act & Assert
        mockMvc.perform(post(ApiConstant.GUEST_API_PREFIX + ApiConstant.CHECKED_OUT_BY_ID_API_ENDPOINT
                        .replace("{id}", guestId.toString()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value(MessageConstant.GUEST_CHECKOUT_SUCCESS));

        verify(guestService, times(1)).checkOut(guestId);
    }

    @Test
    void getCheckedInGuests_ShouldReturnGuestList() throws Exception {
        // Arrange
        Guest guest = new Guest(1L, "John Doe", "101", true);
        List<Guest> guestList = Collections.singletonList(guest);
        Page<Guest> guestPage = new PageImpl<>(guestList, PageRequest.of(0, 10), 1);

        when(guestService.getCheckedInGuests(anyInt(), anyInt(), anyString())).thenReturn(guestPage);

        // Act & Assert
        mockMvc.perform(get(ApiConstant.GUEST_API_PREFIX + ApiConstant.CHECKED_IN_GUESTS_API_ENDPOINT)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value(MessageConstant.CHECKED_IN_GUESTS_RETRIEVED_SUCCESS))
                .andExpect(jsonPath("$.data.content[0].name").value("John Doe"));

        verify(guestService, times(1)).getCheckedInGuests(anyInt(), anyInt(), anyString());
    }
}
