package com.everspark.parceltracking.utility;

import com.everspark.parceltracking.constant.PaginationConstant;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtility {
    private PaginationUtility() {
        throw new UnsupportedOperationException(PaginationConstant.UTILITY_CLASS_NOTE);
    }

    public static Pageable createPageable(int page, int size, String sortBy) {
        String[] sortParams = sortBy.split(PaginationConstant.SORT_BY_SEPARATION);
        Sort sort = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        return PageRequest.of(page, size, sort);
    }
}
