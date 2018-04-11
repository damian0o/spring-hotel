package pl.apso.springhotel.reservation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.apso.springhotel.hotels.Hotel;
import pl.apso.springhotel.hotels.Room;
import pl.apso.springhotel.hotels.RoomRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

  @Mock
  private RoomRepository roomRepository;

  @Mock
  private ReservationRepository reservationRepository;

  @InjectMocks
  private ReservationService reservationService;

  @Test
  public void shouldReturnAvailableRoomsList() {
    // given
    LocalDate start = LocalDate.of(2016, 2, 1);
    LocalDate end = LocalDate.of(2017, 2, 1);
    String city = "Krakow";
    int priceMin = 100;
    int priceMax = 200;

    Hotel hotel = new Hotel(1L, "Roo Inn", "Krakow");
    Room room = new Room(1L, 200, hotel);

    List<Room> returnedRooms = asList(room);
    when(roomRepository.findAllByPriceIsBetweenAndHotelCity(priceMin, priceMax, city))
      .thenReturn(returnedRooms);

    when(reservationRepository.findAllCollidingForRoom(start, end, returnedRooms))
      .thenReturn(Collections.emptyList());
    // when
    List<Room> result = reservationService.getRooms(start, end, city, priceMin, priceMax);
    // then
    assertThat(result).containsExactly(room);

    verify(roomRepository, only())
      .findAllByPriceIsBetweenAndHotelCity(anyInt(), anyInt(), anyString());
    verify(reservationRepository, only())
      .findAllCollidingForRoom(any(LocalDate.class), any(LocalDate.class), any());
  }

}
