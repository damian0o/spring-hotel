package pl.apso.springhotel.reservation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.apso.springhotel.hotel.Hotel;
import pl.apso.springhotel.hotel.Room;
import pl.apso.springhotel.hotel.RoomRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
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

    Hotel hotel = new Hotel(1L, "Roo Inn", "Krakow", null);
    Room room = Room.builder().id(1L).avail(true).price(200).hotel(hotel).build();

    List<Room> returnedRooms = singletonList(room);
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

  @Test
  public void shouldAddReservationToPendingList() throws Exception {
    // given
    Hotel hotel = Hotel.builder().id(1L).name("MyHotel").city("Poznan").build();
    Room room = new Room(1L, 200, hotel, true);
    given(roomRepository.findById(1L)).willReturn(Optional.of(room));
    given(reservationRepository.save(any(Reservation.class)))
        .willReturn(Reservation.builder()
            .id(1L)
            .start(LocalDate.of(2017, 5, 5))
            .end(LocalDate.of(2017, 5, 7))
            .room(room).accepted(false).build());

    // when
    ReservationRequest request = new ReservationRequest(
        LocalDate.of(2017, 5, 5),
        LocalDate.of(2017, 5, 7), 1L);
    ReservationResponse response = reservationService.submitReservation(request);
    // then
    assertThat(response).isEqualTo(new ReservationResponse(1L, 1L, "PENDING"));
  }

  @Test(expected = RoomNotFoundException.class)
  public void shouldThrowRoomNotFoundException() throws Exception {
    // given
    given(roomRepository.findById(1L)).willReturn(Optional.empty());

    // when
    reservationService.submitReservation(
        new ReservationRequest(LocalDate.now(), LocalDate.now(), 1L)
    );
  }

}
