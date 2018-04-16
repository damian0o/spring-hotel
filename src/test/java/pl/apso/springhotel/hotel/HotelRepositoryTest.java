package pl.apso.springhotel.hotel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HotelRepositoryTest {

  @Autowired
  private HotelRepository hotelRepository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void shouldPersistWithRooms() {
    // given
    Hotel hotel = new Hotel("Pirate Bay Hotel", "Poznan");
    Room r1 = Room.builder().price(100).build();
    Room r2 = Room.builder().price(200).build();
    Room r3 = Room.builder().price(300).build();
    Room r4 = Room.builder().price(400).build();
    hotel.setRooms(Arrays.asList(r1, r2, r3, r4));
    // when
    Hotel saved = hotelRepository.save(hotel);
    Optional<Hotel> result = hotelRepository.findById(saved.getId());
    // then
    assertThat(result).isPresent();
    assertThat(result.get().getRooms()).hasSize(4);
  }

  @Test
  public void getHotelById() {
    // given
    Long id = (Long) entityManager.persistAndGetId(new Hotel("Banana Hotel", "Poznan"));

    // when
    Optional<Hotel> maybeResult = hotelRepository.findById(id);

    // then
    assertThat(maybeResult).isPresent();
    Hotel result = maybeResult.get();
    assertThat(result.getId()).isNotNull();
    assertThat(result.getName()).isEqualTo("Banana Hotel");
    assertThat(result.getCity()).isEqualTo("Poznan");
  }

  @Test
  public void getHotelByName() {
    // given
    entityManager.persist(new Hotel("Integral Palace", "Szczecin"));

    // when
    Hotel result = hotelRepository.findByName("Integral Palace");

    // then
    assertThat(result.getId()).isNotNull().isGreaterThan(0);
    assertThat(result.getName()).isEqualTo("Integral Palace");
    assertThat(result.getCity()).isEqualTo("Szczecin");
  }

  @Test
  public void getHotelsByCity() {
    // given
    entityManager.persist(new Hotel("Banana Hotel", "Poznan"));
    entityManager.persist(new Hotel("Integral Palace", "Szczecin"));
    entityManager.persist(new Hotel("Stocznia Hotel", "Szczecin"));

    // when
    List<Hotel> results = hotelRepository.findByCity("Szczecin");

    // then
    assertThat(results).hasSize(2)
        .usingElementComparatorOnFields("name", "city")
        .containsExactlyInAnyOrder(
            new Hotel("Integral Palace", "Szczecin"),
            new Hotel("Stocznia Hotel", "Szczecin")
        );
  }

}
