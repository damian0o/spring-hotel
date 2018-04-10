package pl.apso.springhotel;

import org.assertj.core.api.iterable.Extractor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
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
      .isEqualToIgnoringGivenFields(
        new Hotel("Paradise", "Poznan"), "id"
      );
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

  @Test
  public void getRoomsByHotelCity() {
    // given
    Hotel hotel1 = new Hotel("Su", "Poznan");
    entityManager.persist(new Room(100, hotel1));
    Hotel hotel2 = new Hotel("Sudo", "Poznan");
    entityManager.persist(new Room(100, hotel2));
    Hotel hotel3 = new Hotel("Pwd", "Warsaw");
    entityManager.persist(new Room(100, hotel3));
    // when
    List<Room> rooms = roomRepository.findAllByHotelCity("Poznan");

    // then
    assertThat(rooms).hasSize(2)
      .flatExtracting((Extractor<Room, Hotel>) Room::getHotel)
      .usingElementComparatorIgnoringFields("id")
      .containsExactlyInAnyOrder(
        new Hotel("Su", "Poznan"),
        new Hotel("Sudo", "Poznan")
      );
  }

  @Test
  public void getRoomsByPriceBetweenAndHotelCity() {
    // given
    Hotel hotel1 = new Hotel("Gold Hotel", "Warsaw");
    entityManager.persist(new Room(100, hotel1));
    entityManager.persist(new Room(120, hotel1));
    entityManager.persist(new Room(140, hotel1));
    entityManager.persist(new Room(150, hotel1));
    Hotel hotel2 = new Hotel("Silver Hotel", "Warsaw");
    entityManager.persist(new Room(100, hotel2));
    entityManager.persist(new Room(120, hotel2));
    entityManager.persist(new Room(130, hotel2));
    Hotel hotel3 = new Hotel("Bronze Hotel", "Warsaw");
    entityManager.persist(new Room(110, hotel3));
    Hotel hotel4 = new Hotel("Other", "Poznan");
    entityManager.persist(new Room(120, hotel4));

    // when
    List<Room> rooms = roomRepository
      .findAllByPriceIsBetweenAndHotelCity(120, 140, "Warsaw");
    // then
    assertThat(rooms).hasSize(4)
      .flatExtracting((Extractor<Room, Hotel>) Room::getHotel)
      .usingElementComparator((Comparator<Object>) (o1, o2) -> {
        Hotel h1 = (Hotel) o1;
        Hotel h2 = (Hotel) o2;
        return h1.getName().compareTo(h2.getName());
      })
      .contains(
        new Hotel("Gold Hotel", "Poznan"),
        new Hotel("Silver Hotel", "Poznan")
      ).doesNotContain(
      new Hotel("Bronze Hotel", "Warsaw"),
      new Hotel("Other", "Poznan")
    );
  }

}
