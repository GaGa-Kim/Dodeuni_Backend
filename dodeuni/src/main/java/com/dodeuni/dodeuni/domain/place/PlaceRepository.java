package com.dodeuni.dodeuni.domain.place;

import com.dodeuni.dodeuni.web.dto.place.PlaceListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query("select new com.dodeuni.dodeuni.web.dto.place.PlaceListResponseDto(p.id, p.name, p.category, p.address, p.contact," +
            "p.x, p.y, p.createdDateTime, p.modifiedDateTime, u.id, u.nickname," +
            "(6371 * acos(cos(radians(:y)) * cos(radians(p.y)) * cos(radians(p.x) - radians(:x))" +
            "+ sin(radians(:y)) * sin(radians(p.y)))) as distance, count(p.id) as reviewsCount) " +
            "from Place p " +
            "left join p.user u " +
            "left join p.placeReviewList r " +
            "where p.address like %:keyword% " +
            "and (6371 * acos(cos(radians(:y)) * cos(radians(p.y)) * cos(radians(p.x) - radians(:x)) " +
            "+ sin(radians(:y)) * sin(radians(p.y)))) <= 1.5 " +
            "group by p.id having r.place is not null order by distance desc")
    List<PlaceListResponseDto> getPlaceByLocationAndKeyword(@Param("x") double x, @Param("y") double y, @Param("keyword") String keyword);
    Place findByNameAndAddressAndXAndY(String name, String address, double x, double y);
}
