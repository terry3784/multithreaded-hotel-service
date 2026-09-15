package com.hotel.reservations.landonhotel.repository;

import com.hotel.reservations.landonhotel.entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;



public interface RoomRepository extends CrudRepository<RoomEntity, Long> {

	//RoomEntity findById(Long id);
}
