package pl.apso.springhotel.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
  List<Room> findAllByPrice(int price);
  List<Room> findAllByPriceLessThanEqual(int price);
  List<Room> findAllByHotelCity(String city);
  List<Room> findAllByPriceIsBetweenAndHotelCity(int min, int max, String city);
  List<Room> findAllByHotelName(String name);
}
