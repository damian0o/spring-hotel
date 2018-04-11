package pl.apso.springhotel.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.apso.springhotel.hotels.Room;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  @Query("select r from Reservation r where ((r.fromDate >= ?1 and r.toDate < ?1) or (r.fromDate < ?2 and r.toDate >= ?2))")
  List<Reservation> findAllColliding(LocalDate start, LocalDate end);

  @Query("select r from Reservation r where ((r.fromDate >= ?1 and r.toDate < ?1) or (r.fromDate < ?2 and r.toDate >= ?2)) and r.room in ?3")
  List<Reservation> findAllCollidingForRoom(LocalDate start, LocalDate end, List<Room> room);
}
