package com.dodeuni.dodeuni.service.place;

import com.dodeuni.dodeuni.domain.place.Place;
import com.dodeuni.dodeuni.domain.place.PlaceRepository;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.except.NotFoundException;
import com.dodeuni.dodeuni.service.user.UserService;
import com.dodeuni.dodeuni.web.dto.place.PlaceInquiryDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceListResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceSaveRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    private final UserService userService;

    @Override
    public PlaceResponseDto savePlace(PlaceSaveRequestDto requestDto) {
        Place place = placeRepository.findByNameAndAddressAndXAndY(requestDto.getName(), requestDto.getAddress(), requestDto.getX(), requestDto.getY());
        if (place != null) {
            return new PlaceResponseDto(place);
        }
        User user = userService.findByUserId(requestDto.getUserId());
        Place newPlace = requestDto.toEntity();
        newPlace.setUser(user);
        placeRepository.save(newPlace);
        return new PlaceResponseDto(newPlace);
    }

    @Override
    public List<PlaceListResponseDto> getPlaceList(PlaceInquiryDto placeInquiryDto) {
        return placeRepository.getPlaceByLocationAndKeyword(placeInquiryDto.getX(), placeInquiryDto.getY(), placeInquiryDto.getKeyword());
    }

    @Override
    public Place findByPlaceId(Long placeId) {
        return placeRepository.findById(placeId).orElseThrow(NotFoundException::new);
    }

    @Override
    public PlaceResponseDto getPlace(Long placeId) {
        Place place = findByPlaceId(placeId);
        return new PlaceResponseDto(place);
    }
}

