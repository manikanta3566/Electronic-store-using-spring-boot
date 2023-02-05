package com.project.electronic.store.util;

import com.project.electronic.store.dto.ListingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CommonUtil {

    public static final Set<String> imageFileExtensions=Set.of(".png",".jpg");

    public static <E, V> ListingResponse<V> getListingResponse(Page<E> entity, Class<V> type) {
        List<V> dtoList = entity.stream().map(user -> new ModelMapper().map(user, type)).collect(Collectors.toList());
        ListingResponse listingResponse = new ListingResponse<>();
        listingResponse.setContents(dtoList);
        listingResponse.setPageNumber(entity.getNumber());
        listingResponse.setPageSize(entity.getSize());
        listingResponse.setTotalPages(entity.getTotalPages());
        listingResponse.setLastPage(entity.isLast());
        return listingResponse;
    }

    public static String getExtensionFromFile(MultipartFile file){
        return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    }

    public static String getFileNameWithTimestamp(MultipartFile file){
        return file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + "_"
                + System.currentTimeMillis();
    }
}
