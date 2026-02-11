package com.maiboroda.GoGo.mapper;
import com.maiboroda.GoGo.dto.ReviewResponseDTO;
import com.maiboroda.GoGo.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "dateOfBirth", source = "user.dateOfBirth")
    ReviewResponseDTO toDto(Review review);
    List<ReviewResponseDTO> toDtoList(List<Review> reviews);

}

