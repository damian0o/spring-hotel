package pl.apso.springhotel.hotel;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Hotels and room management service.
 */
@Service
public class HotelManagementService {

  private HotelRepository hotelRepository;
  private RoomRepository roomRepository;

  public HotelManagementService(HotelRepository hotelRepository, RoomRepository roomRepository) {
    this.hotelRepository = hotelRepository;
    this.roomRepository = roomRepository;
  }

  /**
   * Allow to list all available hotels from database
   *
   * @return List of hotels
   */
  List<Hotel> getAll() {
    return hotelRepository.findAll();
  }

  /**
   * Persist new hotel to database.
   *
   * @param hotel - entity that should be persisted
   * @return persisted hotel instance
   * @throws HotelAlreadyExistsException
   */
  Hotel create(Hotel hotel) throws HotelAlreadyExistsException {
    Hotel byName = hotelRepository.findByName(hotel.getName());
    if (byName != null) {
      throw new HotelAlreadyExistsException();
    }
    if (hotel.getRooms() != null) {
      hotel.getRooms().forEach(x -> {
        x.setHotel(hotel);
      });
    }
    return hotelRepository.save(hotel);
  }

  /**
   * Get hotel information by its name.
   *
   * @param name - to lookup for hotel
   * @return hotel with searched name
   * @throws HotelNotFoundException
   */
  Hotel getHotel(String name) throws HotelNotFoundException {
    Hotel byName = hotelRepository.findByName(name);
    if (byName == null) {
      throw new HotelNotFoundException();
    }
    return byName;
  }

  /**
   * Get hotel rooms list
   *
   * @param hotelName
   * @return List of rooms for hotel
   */
  Collection<Room> getRooms(String hotelName) throws HotelNotFoundException {
    Hotel byName = hotelRepository.findByName(hotelName);
    if (byName == null) {
      throw new HotelNotFoundException();
    }
    return byName.getRooms();
  }

  /**
   * Add room to specified hotel
   *
   * @param hotelName - hotel name to which new room will be added
   * @param room      - room entity for specified hotel
   * @return persisted room entity with id
   */
  Room addRoom(String hotelName, Room room) throws HotelNotFoundException {
    Hotel existingHotel = hotelRepository.findByName(hotelName);
    if (existingHotel == null) {
      throw new HotelNotFoundException();
    }
    room.setHotel(existingHotel);
    return roomRepository.save(room);
  }
}
