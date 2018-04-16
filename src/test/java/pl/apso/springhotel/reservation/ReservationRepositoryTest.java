package pl.apso.springhotel.reservation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.apso.springhotel.hotel.Hotel;
import pl.apso.springhotel.hotel.Room;

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
    Room room1 = Room.builder().price(200).hotel(hotel).build();
    entityManager.persist(room1);
    entityManager.persist(Reservation.builder().start(start.minusDays(2)).end(start).room(room1).build());
    entityManager.persist(Reservation.builder().start(end).end(end.plusDays(2)).room(room1).build());
    entityManager.persist(Reservation.builder().start(end.plusDays(2)).end(end.plusDays(4)).room(room1).build());
    entityManager.persist(Reservation.builder().start(end.plusDays(1)).end(end.plusDays(4)).room(room1).build());
    entityManager.persist(Reservation.builder().start(start.minusDays(1)).end(start).room(room1).build());
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
    Room room1 = Room.builder().price(200).hotel(hotel).build();
    entityManager.persist(room1);

    Reservation res1 = Reservation.builder().start(start).end(end.plusDays(1)).room(room1).build();
    entityManager.persist(res1);
    // when
    List<Reservation> result = reservationRepository.findAllColliding(start, end);
    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualToComparingOnlyGivenFields(res1, "start", "end");
  }

  @Test
  public void shouldNotFoundAnyCollidingReservationsForRoom() {
    // given
    LocalDate start = LocalDate.of(2018, 4, 1);
    LocalDate end = start.plusDays(4);

    Hotel hotel = new Hotel("Temp", "City");
    Room wantedRoom = Room.builder().price(200).hotel(hotel).build();

    entityManager.persist(wantedRoom);
    entityManager.persist(Reservation.builder().start(start.minusDays(2)).end(start).room(wantedRoom).build());
    entityManager.persist(Reservation.builder().start(end).end(end.plusDays(2)).room(wantedRoom).build());
    entityManager.persist(Reservation.builder().start(end.plusDays(2)).end(end.plusDays(4)).room(wantedRoom).build());
    entityManager.persist(Reservation.builder().start(end.plusDays(1)).end(end.plusDays(4)).room(wantedRoom).build());
    entityManager.persist(Reservation.builder().start(start.minusDays(1)).end(start).room(wantedRoom).build());
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
    Room wantedRoom = Room.builder().price(200).hotel(hotel).build();
    entityManager.persist(wantedRoom);

    Reservation res1 = Reservation.builder().start(start).end(end.plusDays(1)).room(wantedRoom).build();
    entityManager.persist(res1);
    // when
    List<Reservation> result = reservationRepository.findAllCollidingForRoom(start, end, singletonList(wantedRoom));
    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualToComparingOnlyGivenFields(res1, "start", "end");
  }

  @Test
  public void shouldNotFindAnyCollisionsWhenRoomIsDifferent() {
    // given
    LocalDate start = LocalDate.of(2018, 4, 1);
    LocalDate end = start.plusDays(4);

    Hotel hotel = new Hotel("Temp", "City");
    Room reservedRoom = Room.builder().price(100).hotel(hotel).build();
    entityManager.persist(reservedRoom);

    Reservation res1 = Reservation.builder().start(start).end(end.plusDays(1)).room(reservedRoom).build();

    entityManager.persist(res1);

    Room wantedRoom = Room.builder().price(200).hotel(hotel).build();
    entityManager.persist(wantedRoom);
    // when
    List<Reservation> result = reservationRepository.findAllCollidingForRoom(start, end, singletonList(wantedRoom));
    // then
    assertThat(result).hasSize(0);
  }


}
