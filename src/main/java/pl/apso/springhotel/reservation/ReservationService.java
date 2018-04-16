package pl.apso.springhotel.reservation;

import org.springframework.stereotype.Service;
import pl.apso.springhotel.hotel.Room;
import pl.apso.springhotel.hotel.RoomRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service is responsible for business logic for reservation
 */
@Service
public class ReservationService {

  private final ReservationRepository reservationRepository;

  private final RoomRepository roomRepository;

  public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
    this.reservationRepository = reservationRepository;
    this.roomRepository = roomRepository;
  }

  public List<Room> getRooms(
      LocalDate start, LocalDate end, String city, int priceMin, int priceMax) {
    List<Room> rooms = roomRepository.findAllByPriceIsBetweenAndHotelCity(priceMin, priceMax, city);
    List<Long> collidingIds = reservationRepository
        .findAllCollidingForRoom(start, end, rooms)
        .stream().map(x -> x.getRoom().getId())
        .collect(Collectors.toList());

    return rooms.stream()
        .filter(Room::isAvail)
        .filter(room -> !collidingIds.contains(room.getId()))
        .collect(Collectors.toList());
  }

  public ReservationResponse submitReservation(ReservationRequest in) throws RoomNotFoundException {
    Optional<Room> r = roomRepository.findById(in.getRoomId());
    if (!r.isPresent()) {
      throw new RoomNotFoundException();
    }
    return r.map(x ->
        Reservation.builder()
            .start(in.getStart())
            .end(in.getEnd())
            .room(x)
            .accepted(false).build())
        .map(reservationRepository::save)
        .map(x -> ReservationResponse.builder()
            .id(x.getId())
            .roomId(in.getRoomId())
            .status("PENDING").build())
        .orElse(ReservationResponse.builder().status("ERROR").build());
  }
}
