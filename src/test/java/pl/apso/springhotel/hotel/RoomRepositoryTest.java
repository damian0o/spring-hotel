package pl.apso.springhotel.hotel;

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

    Room room = Room.builder().price(150).hotel(hotel).build();
    Long id = (Long) entityManager.persistAndGetId(room);
    // when
    Optional<Room> result = roomRepository.findById(id);
    // then
    assertThat(result).isPresent();
    Room roomResult = result.get();
    assertThat(roomResult.getPrice()).isEqualTo(150);
    assertThat(roomResult.getHotel())
      .isEqualToIgnoringGivenFields(
        new Hotel("Paradise", "Poznan"), "id"
      );
  }

  @Test
  public void findRoomByPrice() {
    // given
    Hotel hotel = new Hotel("JemHotel", "Temp");
    entityManager.persist(Room.builder().price(100).hotel(hotel).build());
    entityManager.persist(Room.builder().price(200).hotel(hotel).build());
    entityManager.persist(Room.builder().price(100).hotel(hotel).build());

    // when
    List<Room> rooms = roomRepository.findAllByPrice(100);

    // then
    assertThat(rooms).hasSize(2);
  }

  @Test
  public void getRoomsWithPriceLessThenOrEq() {
    // given
    Hotel hotel1 = new Hotel("Marina", "Temp");
    entityManager.persist(Room.builder().price(100).hotel(hotel1).build());
    entityManager.persist(Room.builder().price(100).hotel(hotel1).build());
    entityManager.persist(Room.builder().price(120).hotel(hotel1).build());
    entityManager.persist(Room.builder().price(130).hotel(hotel1).build());
    entityManager.persist(Room.builder().price(131).hotel(hotel1).build());

    Hotel hotel2 = new Hotel("Yolo INN", "Context");
    entityManager.persist(Room.builder().price(100).hotel(hotel2).build());
    // when
    List<Room> rooms = roomRepository.findAllByPriceLessThanEqual(130);
    // then
    assertThat(rooms).hasSize(5);
  }

  @Test
  public void getRoomsByHotelCity() {
    // given
    Hotel hotel1 = new Hotel("Su", "Poznan");
    entityManager.persist(Room.builder().price(100).hotel(hotel1).build());
    Hotel hotel2 = new Hotel("Sudo", "Poznan");
    entityManager.persist(Room.builder().price(100).hotel(hotel2).build());
    Hotel hotel3 = new Hotel("Pwd", "Warsaw");
    entityManager.persist(Room.builder().price(100).hotel(hotel3).build());
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
    entityManager.persist(Room.builder().price(120).hotel(hotel1).build());
    entityManager.persist(Room.builder().price(100).hotel(hotel1).build());
    entityManager.persist(Room.builder().price(140).hotel(hotel1).build());
    entityManager.persist(Room.builder().price(150).hotel(hotel1).build());
    Hotel hotel2 = new Hotel("Silver Hotel", "Warsaw");
    entityManager.persist(Room.builder().price(100).hotel(hotel2).build());
    entityManager.persist(Room.builder().price(120).hotel(hotel2).build());
    entityManager.persist(Room.builder().price(130).hotel(hotel2).build());
    Hotel hotel3 = new Hotel("Bronze Hotel", "Warsaw");
    entityManager.persist(Room.builder().price(110).hotel(hotel3).build());
    Hotel hotel4 = new Hotel("Other", "Poznan");
    entityManager.persist(Room.builder().price(120).hotel(hotel4).build());

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

  @Test
  public void getRoomsByHotelName() {
    // given
    Hotel hotel1 = new Hotel("Primary", "Poznan");
    entityManager.persist(Room.builder().price(100).hotel(hotel1).build());
    Hotel hotel2 = new Hotel("Secondary", "Poznan");
    entityManager.persist(Room.builder().price(100).hotel(hotel2).build());
    Hotel hotel3 = new Hotel("Third", "Warsaw");
    entityManager.persist(Room.builder().price(100).hotel(hotel3).build());
    // when
    List<Room> rooms = roomRepository.findAllByHotelName("Primary");

    // then
    assertThat(rooms).hasSize(1)
      .usingElementComparatorIgnoringFields("id")
      .containsExactlyInAnyOrder(
        Room.builder().price(100).hotel(hotel1).build()
      );
  }

}
