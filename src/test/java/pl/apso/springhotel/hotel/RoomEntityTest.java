package pl.apso.springhotel.hotel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.singletonList;
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
    Room room = Room.builder().price(100).hotel(hotel).build();
    hotel.setRooms(singletonList(room));
    // when
    Room result = entityManager.persistFlushFind(room);
    // then
    assertThat(result.getPrice()).isEqualTo(100);
    assertThat(result.getHotel().getName()).isEqualTo("Big Hotel");
    assertThat(result.getHotel().getCity()).isEqualTo("Warsaw");
  }

}
