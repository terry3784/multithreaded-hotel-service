package com.hotel.reservations.landonhotel.repository;


import com.hotel.reservations.landonhotel.entity.ReservationEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {
}
