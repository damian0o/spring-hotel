package pl.apso.springhotel.hotels;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HotelServiceTest {

  @Mock
  private HotelRepository hotelRepository;

  @Mock
  private RoomRepository roomRepository;

  private HotelService hotelService;

  @Before
  public void setup() {
    hotelService = new HotelService(hotelRepository, roomRepository);
  }

  @Test
  public void shouldReturnAllHotels() {
    // given
    Hotel first = new Hotel(1L, "1", "1");
    Hotel second = new Hotel(2L, "2", "2");
    when(hotelRepository.findAll()).thenReturn(Arrays.asList(first, second));
    // when
    List<Hotel> result = hotelService.getAll();
    // then
    assertThat(result).containsExactlyInAnyOrder(first, second);
  }

  @Test
  public void shouldSaveHotelInRepository() {
    // given
    Hotel hotel = new Hotel("name", "city");
    // when
    hotelService.create(hotel);
    // then
    verify(hotelRepository).save(eq(hotel));
  }

  @Test(expected = HotelAlreadyExistsException.class)
  public void shouldThrowAlreadyExistsException() {
    // given
    Hotel hotel = new Hotel("name", "city");
    when(hotelRepository.findByName("name")).thenReturn(hotel);
    // when
    hotelService.create(hotel);
  }

  @Test
  public void shouldReturnHotelByName() {
    Hotel hotel = new Hotel("simple", "poznan");
    when(hotelRepository.findByName("simple")).thenReturn(hotel);
    // when
    Hotel result = hotelService.getHotel("simple");
    // then
    assertThat(result).isEqualTo(hotel);
  }

  @Test(expected = HotelNotFoundException.class)
  public void shouldThrowNotFound() {
    // when
    hotelService.getHotel("simple");
  }

}
