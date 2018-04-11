package pl.apso.springhotel.hotels;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

  private HotelRepository hotelRepository;
  private RoomRepository roomRepository;

  public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository) {
    this.hotelRepository = hotelRepository;
    this.roomRepository = roomRepository;
  }

  public List<Hotel> getAll() {
    return hotelRepository.findAll();
  }

  Hotel create(Hotel hotel) {
    Hotel byName = hotelRepository.findByName(hotel.getName());
    if (byName != null) {
      throw new HotelAlreadyExistsException();
    }
    return hotelRepository.save(hotel);
  }

  Hotel getHotel(String name) {
    Hotel byName = hotelRepository.findByName(name);
    if (byName == null) {
      throw new HotelNotFoundException();
    }
    return byName;
  }

  List<Room> getRooms(String hotelName) {
    return roomRepository.findAllByHotelName(hotelName);
  }

  Room addRoom(String hotelName, Room room) {
    Hotel existingHotel = hotelRepository.findByName(hotelName);
    if (existingHotel == null) {
      throw new HotelNotFoundException();
    }
    room.setHotel(existingHotel);
    return roomRepository.save(room);
  }
}
