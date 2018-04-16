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
    roomRepository.save(Room.builder()
        .avail(true).price(100).hotel(hotel).build());

    LocalDate start = LocalDate.of(2017, 2, 1);
    LocalDate end = LocalDate.of(2017, 2, 2);
    String city = "Kolobrzeg";
    int min = 100;
    int max = 140;

    // when
    List<Room> rooms = reservationService.getRooms(start, end, city, min, max);

    // then
    assertThat(rooms).hasSize(1);
    assertThat(rooms).usingElementComparatorIgnoringFields("id", "hotel")
        .containsExactly(Room.builder().avail(true).price(100).build());
  }

  @Test
  public void shouldNotReturnNonAvailRooms() {
    // given
    Hotel hotel = new Hotel("Marin2", "Kolobrzeg");
    roomRepository.save(Room.builder().price(100).hotel(hotel).avail(false).build());

    LocalDate start = LocalDate.of(2017, 2, 1);
    LocalDate end = LocalDate.of(2017, 2, 2);
    String city = "Kolobrzeg";
    int min = 100;
    int max = 140;

    // when
    List<Room> rooms = reservationService.getRooms(start, end, city, min, max);

    // then
    assertThat(rooms).hasSize(0);
  }

}
