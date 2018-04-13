package pl.apso.springhotel.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
  Hotel findByName(String name);
  List<Hotel> findByCity(String name);
}
