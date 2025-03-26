package com.hrs.parceltracking.utility;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaginationUtilityTest {

    @Test
    void createPageable_ShouldReturnPageable_WhenValidSortByProvided() {
        // Given
        int page = 1;
        int size = 10;
        String sortBy = "id,asc";

        // When
        Pageable pageable = PaginationUtility.createPageable(page, size, sortBy);

        // Then
        assertNotNull(pageable);
        assertTrue(pageable instanceof PageRequest);
        assertEquals(page, pageable.getPageNumber());
        assertEquals(size, pageable.getPageSize());
        assertEquals(Sort.by(Sort.Direction.ASC, "id"), pageable.getSort());
    }

    @Test
    void createPageable_ShouldReturnPageable_WhenSortByDescending() {
        // Given
        int page = 0;
        int size = 5;
        String sortBy = "name,desc";

        // When
        Pageable pageable = PaginationUtility.createPageable(page, size, sortBy);

        // Then
        assertNotNull(pageable);
        assertEquals(page, pageable.getPageNumber());
        assertEquals(size, pageable.getPageSize());
        assertEquals(Sort.by(Sort.Direction.DESC, "name"), pageable.getSort());
    }

    @Test
    void createPageable_ShouldThrowException_WhenSortByHasInvalidFormat() {
        // Given
        int page = 0;
        int size = 10;
        String sortBy = "invalid_format";

        // When & Then
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> PaginationUtility.createPageable(page, size, sortBy));
    }

    @Test
    void createPageable_ShouldThrowException_WhenSortDirectionIsInvalid() {
        // Given
        int page = 0;
        int size = 10;
        String sortBy = "id,wrong_direction";

        // When & Then
        assertThrows(IllegalArgumentException.class,
                () -> PaginationUtility.createPageable(page, size, sortBy));
    }

    @Test
    void paginationUtilityConstructor_ShouldThrowException_WhenCalled() throws Exception {
        Constructor<PaginationUtility> constructor = PaginationUtility.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }
}
