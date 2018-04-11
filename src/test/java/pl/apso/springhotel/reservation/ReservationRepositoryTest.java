package pl.apso.springhotel.reservation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.apso.springhotel.hotels.Hotel;
import pl.apso.springhotel.hotels.Room;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationRepositoryTest {

  @Autowired
  private ReservationRepository reservationRepository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void shouldNotFoundAnyCollidingReservations() {
    // given
    LocalDate start = LocalDate.of(2018, 4, 1);
    LocalDate end = start.plusDays(4);

    Hotel hotel = new Hotel("Temp", "City");
    Room room1 = new Room(200, hotel);
    entityManager.persist(room1);
    entityManager.persist(new Reservation(start.minusDays(2), start, room1));
    entityManager.persist(new Reservation(end, end.plusDays(2), room1));
    entityManager.persist(new Reservation(end.plusDays(2), end.plusDays(4), room1));
    entityManager.persist(new Reservation(end.plusDays(1), end.plusDays(4), room1));
    entityManager.persist(new Reservation(start.minusDays(1), start, room1));
    // when
    List<Reservation> result = reservationRepository.findAllColliding(start, end);
    // then
    assertThat(result).isEmpty();
  }

  @Test
  public void shouldFindCollidingReservation() {
    // given
    LocalDate start = LocalDate.of(2018, 4, 1);
    LocalDate end = start.plusDays(4);

    Hotel hotel = new Hotel("Temp", "City");
    Room room1 = new Room(200, hotel);
    entityManager.persist(room1);

    Reservation res1 = new Reservation(start, end.plusDays(1), room1);
    entityManager.persist(res1);
    // when
    List<Reservation> result = reservationRepository.findAllColliding(start, end);
    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualToComparingOnlyGivenFields(res1, "fromDate", "toDate");
  }

  @Test
  public void shouldNotFoundAnyCollidingReservationsForRoom() {
    // given
    LocalDate start = LocalDate.of(2018, 4, 1);
    LocalDate end = start.plusDays(4);

    Hotel hotel = new Hotel("Temp", "City");
    Room wantedRoom = new Room(200, hotel);

    entityManager.persist(wantedRoom);
    entityManager.persist(new Reservation(start.minusDays(2), start, wantedRoom));
    entityManager.persist(new Reservation(end, end.plusDays(2), wantedRoom));
    entityManager.persist(new Reservation(end.plusDays(2), end.plusDays(4), wantedRoom));
    entityManager.persist(new Reservation(end.plusDays(1), end.plusDays(4), wantedRoom));
    entityManager.persist(new Reservation(start.minusDays(1), start, wantedRoom));
    // when
    List<Reservation> result = reservationRepository.findAllCollidingForRoom(start, end, singletonList(wantedRoom));
    // then
    assertThat(result).isEmpty();
  }

  @Test
  public void shouldFindCollidingReservationForRoom() {
    // given
    LocalDate start = LocalDate.of(2018, 4, 1);
    LocalDate end = start.plusDays(4);

    Hotel hotel = new Hotel("Temp", "City");
    Room wantedRoom = new Room(200, hotel);
    entityManager.persist(wantedRoom);

    Reservation res1 = new Reservation(start, end.plusDays(1), wantedRoom);
    entityManager.persist(res1);
    // when
    List<Reservation> result = reservationRepository.findAllCollidingForRoom(start, end, singletonList(wantedRoom));
    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualToComparingOnlyGivenFields(res1, "fromDate", "toDate");
  }

  @Test
  public void shouldNotFindAnyCollisionsWhenRoomIsDifferent() {
    // given
    LocalDate start = LocalDate.of(2018, 4, 1);
    LocalDate end = start.plusDays(4);

    Hotel hotel = new Hotel("Temp", "City");
    Room reservedRoom = new Room(100, hotel);
    entityManager.persist(reservedRoom);

    Reservation res1 = new Reservation(start, end.plusDays(1), reservedRoom);

    entityManager.persist(res1);

    Room wantedRoom = new Room(200, hotel);
    entityManager.persist(wantedRoom);
    // when
    List<Reservation> result = reservationRepository.findAllCollidingForRoom(start, end, singletonList(wantedRoom));
    // then
    assertThat(result).hasSize(0);
  }


}
