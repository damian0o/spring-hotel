package pl.apso.springhotel;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    LocalDate now = LocalDate.now();
    Reservation reservation =
      new Reservation(now, now.plus(1, DAYS));

    // when
    Reservation result = testEntityManager.persistFlushFind(reservation);

    // then
    assertThat(result)
      .isEqualToIgnoringGivenFields(
        new Reservation(now, now.plus(1, DAYS)), "id"
      );
  }

}
