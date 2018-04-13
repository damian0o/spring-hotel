package pl.apso.springhotel.reservation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.apso.springhotel.hotel.Hotel;
import pl.apso.springhotel.hotel.Room;
import pl.apso.springhotel.hotel.RoomRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class ReservationServiceIntegrationTest {

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private RoomRepository roomRepository;

  @Test
  public void shouldReturnAvailRooms() {
    // given
    Hotel hotel = new Hotel("Marin", "Kolobrzeg");
    roomRepository.save(new Room(100, hotel));

    LocalDate start = LocalDate.of(2017, 2, 1);
    LocalDate end = LocalDate.of(2017, 2, 2);
    String city = "Kolobrzeg";
    int min = 100;
    int max = 140;

    // when
    List<Room> rooms = reservationService.getRooms(start, end, city, min, max);

    // then
    assertThat(rooms).hasSize(1);
    assertThat(rooms).usingElementComparatorIgnoringFields("hotel")
        .containsExactly(new Room(1L, 100, null));
  }

}
