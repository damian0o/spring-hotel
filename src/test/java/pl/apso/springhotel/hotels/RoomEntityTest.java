package pl.apso.springhotel.hotels;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.apso.springhotel.hotels.Hotel;
import pl.apso.springhotel.hotels.Room;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomEntityTest {

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void shouldPersistRoom() {
    // given
    Hotel hotel = new Hotel("Big Hotel", "Warsaw");
    Room room = new Room(100, hotel);
    // when
    Room result = entityManager.persistFlushFind(room);
    // then
    assertThat(result.getPrice()).isEqualTo(100);
    assertThat(result.getHotel()).isEqualToIgnoringGivenFields(hotel, "id");
  }

}
