package pl.apso.springhotel.reservation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.apso.springhotel.hotels.Hotel;
import pl.apso.springhotel.hotels.Room;
import pl.apso.springhotel.reservation.Reservation;
import pl.apso.springhotel.reservation.ReservationRepository;

import java.util.Arrays;
import java.util.List;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationRepositoryTest {

  @Autowired
  private ReservationRepository reservationRepository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void findNotCollidingReservations() {
    // given
    Hotel hotel = new Hotel("Yolo", "Warsaw");
    Room room = new Room(12, hotel);
    entityManager.persist(room);
    entityManager.persist(
      new Reservation(
        of(2019, 1, 1),
        of(2019, 1, 3),
        room)
    );
    entityManager.persist(
      new Reservation(
        of(2019, 1, 3),
        of(2019, 1, 5), room
      )
    );
    entityManager.persist(
      new Reservation(
        of(2019, 1, 2),
        of(2019, 1, 4), room
      ));
    // when
    List<Reservation> allForGivenDate =
      reservationRepository.findAllNotColliding(
        of(2019, 1, 3),
        of(2019, 1, 5)
      );
    // then
    assertThat(allForGivenDate)
      .hasSize(1)
      .usingElementComparatorIgnoringFields("id", "room")
      .containsExactlyInAnyOrder(new Reservation(
        of(2019, 1, 1),
        of(2019, 1, 3),
        room)
      );
  }

  @Test
  public void shouldFindFreeDateForGivenPeriodAndRoom() {
    Hotel hotel = new Hotel("Yolo", "Warsaw");
    Room room1 = new Room(120, hotel);
    Room room2 = new Room(120, hotel);
    entityManager.persist(room1);
    entityManager.persist(room2);
    entityManager.persist(
      new Reservation(
        of(2019, 1, 1),
        of(2019, 1, 3),
        room1)
    );
    entityManager.persist(
      new Reservation(
        of(2019, 1, 1),
        of(2019, 1, 3),
        room2
      )
    );

    // when
    List<Reservation> allForGivenDate =
      reservationRepository.findAllNotCollidingforRoom(
        of(2019, 1, 3),
        of(2019, 1, 5),
        Arrays.asList(room1));

    // then
    assertThat(allForGivenDate)
      .hasSize(1)
      .usingElementComparatorIgnoringFields("id", "room")
      .containsExactlyInAnyOrder(new Reservation(
        of(2019, 1, 1),
        of(2019, 1, 3),
        room1)
      );
  }

}
