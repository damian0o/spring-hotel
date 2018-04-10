package pl.apso.springhotel.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.apso.springhotel.hotels.Room;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  @Query("select r from Reservation r where r.fromDate > ?2 or r.toDate <= ?1")
  List<Reservation> findAllNotColliding(LocalDate start, LocalDate end);

  @Query("select r from Reservation r where (r.fromDate > ?2 or r.toDate <= ?1) and r.room not in ?3")
  List<Reservation> findAllNotCollidingforRoom(LocalDate start, LocalDate end, List<Room> room1);
}
