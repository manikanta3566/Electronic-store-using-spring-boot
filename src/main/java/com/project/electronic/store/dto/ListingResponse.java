package com.project.electronic.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingResponse <T>{

    private T contents;

    private int pageNumber;

    private int pageSize;

    private int totalPages;

    private boolean lastPage;
}
