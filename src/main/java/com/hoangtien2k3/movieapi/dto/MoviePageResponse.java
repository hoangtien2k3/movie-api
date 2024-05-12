package com.hoangtien2k3.movieapi.dto;

import java.util.List;

public record MoviePageResponse(
        List<MovieDto> movieDtos,
        Integer pageNumber,
        Integer pageSize,
        long totalElements,
        int totalPages,
        boolean isLast
) {
}
