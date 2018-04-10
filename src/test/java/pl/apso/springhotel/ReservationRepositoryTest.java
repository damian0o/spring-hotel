package pl.apso.springhotel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

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
    entityManager.persist(
      new Reservation(
        of(2019, 1, 1),
        of(2019, 1, 3))
    );
    entityManager.persist(
      new Reservation(
        of(2019, 1, 3),
        of(2019, 1, 5))
    );
    entityManager.persist(
      new Reservation(
        of(2019, 1, 2),
        of(2019, 1, 4))
    );
    // when
    List<Reservation> allForGivenDate =
      reservationRepository.findAllNotColliding(
        of(2019, 1, 3),
        of(2019, 1, 5)
      );
    // then
    assertThat(allForGivenDate)
      .hasSize(1)
      .usingElementComparatorIgnoringFields("id")
      .containsExactlyInAnyOrder(new Reservation(
          of(2019, 1, 1),
          of(2019, 1, 3))
      );
  }


}
