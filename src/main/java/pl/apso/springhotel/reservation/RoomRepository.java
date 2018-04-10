package pl.apso.springhotel.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.apso.springhotel.hotels.Room;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
  List<Room> findAllByPrice(int price);
  List<Room> findAllByPriceLessThanEqual(int price);
  List<Room> findAllByHotelCity(String poznan);
  List<Room> findAllByPriceIsBetweenAndHotelCity(int min, int max, String city);
}
