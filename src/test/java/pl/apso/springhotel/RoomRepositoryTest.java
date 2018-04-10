package pl.apso.springhotel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomRepositoryTest {

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void getById() {
    // given
    Hotel hotel = new Hotel("Paradise", "Poznan");
    Long id = (Long) entityManager.persistAndGetId(new Room(150, hotel));
    // when
    Optional<Room> result = roomRepository.findById(id);
    // then
    assertThat(result).isPresent();
    Room room = result.get();
    assertThat(room.getPrice()).isEqualTo(150);
    assertThat(room.getHotel())
      .isEqualToIgnoringGivenFields(new Hotel("Paradise", "Poznan"), "id");
  }

  @Test
  public void findRoomByPrice() {
    // given
    Hotel hotel = new Hotel("JemHotel", "Temp");
    entityManager.persist(new Room(100, hotel));
    entityManager.persist(new Room(200, hotel));
    entityManager.persist(new Room(100, hotel));

    // when
    List<Room> rooms = roomRepository.findAllByPrice(100);

    // then
    assertThat(rooms).hasSize(2);
  }

  @Test
  public void getRoomsWithPriceLessThenOrEq() {
    // given
    Hotel hotel1 = new Hotel("Marina", "Temp");
    entityManager.persist(new Room(100, hotel1));
    entityManager.persist(new Room(100, hotel1));
    entityManager.persist(new Room(120, hotel1));
    entityManager.persist(new Room(130, hotel1));
    entityManager.persist(new Room(131, hotel1));

    Hotel hotel2 = new Hotel("Yolo INN", "Context");
    entityManager.persist(new Room(100, hotel2));
    // when
    List<Room> rooms = roomRepository.findAllByPriceLessThanEqual(130);
    // then
    assertThat(rooms).hasSize(5);
  }

}
