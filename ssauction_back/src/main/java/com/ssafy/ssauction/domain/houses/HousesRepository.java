package com.ssafy.ssauction.domain.houses;

import com.ssafy.ssauction.web.dto.Houses.HousesItemsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HousesRepository extends JpaRepository<Houses,Long> {

    Page<Houses> findByHouseTitleContainingAndHouseStatus(PageRequest pageRequest, String houseTitle, int houseStatus);

}
