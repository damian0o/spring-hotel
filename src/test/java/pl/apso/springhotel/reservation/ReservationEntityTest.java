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

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationEntityTest {
  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  public void shouldPersistReservation() {
    // given
    Hotel hotel = new Hotel("Grenada Hotel", "Poznan");
    Room room = new Room(200, hotel);

    testEntityManager.persist(room);

    LocalDate now = LocalDate.now();
    Reservation reservation =
      new Reservation(now, now.plus(1, DAYS), room);

    // when
    Reservation result = testEntityManager.persistFlushFind(reservation);

    // then
    assertThat(result)
      .isEqualToIgnoringGivenFields(
        new Reservation(now, now.plus(1, DAYS), room), "id", "room"
      );
  }

}
