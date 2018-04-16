package pl.apso.springhotel.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Allow to access hotel entity from database.
 */
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
  Hotel findByName(String name);
  List<Hotel> findByCity(String name);
}
