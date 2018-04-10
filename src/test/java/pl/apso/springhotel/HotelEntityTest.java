package pl.apso.springhotel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HotelEntityTest {

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void hotelPersistEntity() {
    // given
    Hotel hotelToPersist = new Hotel("JUnit Bay", "Gdansk");

    // when
    Hotel result = entityManager.persistFlushFind(hotelToPersist);

    // then
    assertThat(result.getId()).isNotNull().isGreaterThan(0);
    assertThat(result.getName()).isEqualTo("JUnit Bay");
    assertThat(result.getCity()).isEqualTo("Gdansk");
  }

  @Test
  public void hotelGetById() {
    // given
    Hotel hotelToPersist = new Hotel("Assert House", "Kolobrzeg");
    Long id = (Long) entityManager.persistAndGetId(hotelToPersist);
    // when
    Hotel result = entityManager.find(Hotel.class, id);

    assertThat(result.getId()).isNotNull().isGreaterThan(0);
    assertThat(result.getName()).isEqualTo("Assert House");
    assertThat(result.getCity()).isEqualTo("Kolobrzeg");
  }

}
