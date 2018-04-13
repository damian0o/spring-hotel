package pl.apso.springhotel.hotel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HotelManagementServiceTest {

  @Mock
  private HotelRepository hotelRepository;

  @Mock
  private RoomRepository roomRepository;

  private HotelManagementService hotelManagementService;

  @Before
  public void setup() {
    hotelManagementService = new HotelManagementService(hotelRepository, roomRepository);
  }

  @Test
  public void shouldReturnAllHotels() {
    // given
    Hotel first = new Hotel(1L, "1", "1", emptyList());
    Hotel second = new Hotel(2L, "2", "2", emptyList());
    when(hotelRepository.findAll()).thenReturn(Arrays.asList(first, second));
    // when
    List<Hotel> result = hotelManagementService.getAll();
    // then
    assertThat(result).containsExactlyInAnyOrder(first, second);
  }

  @Test
  public void shouldSaveHotelInRepository() throws Exception {
    // given
    Hotel hotel = new Hotel("name", "city");
    // when
    hotelManagementService.create(hotel);
    // then
    verify(hotelRepository).save(eq(hotel));
  }

  @Test(expected = HotelAlreadyExistsException.class)
  public void shouldThrowAlreadyExistsException() throws Exception {
    // given
    Hotel hotel = new Hotel("name", "city");
    when(hotelRepository.findByName("name")).thenReturn(hotel);
    // when
    hotelManagementService.create(hotel);
  }

  @Test
  public void shouldReturnHotelByName() throws Exception {
    Hotel hotel = new Hotel("simple", "poznan");
    when(hotelRepository.findByName("simple")).thenReturn(hotel);
    // when
    Hotel result = hotelManagementService.getHotel("simple");
    // then
    assertThat(result).isEqualTo(hotel);
  }

  @Test(expected = HotelNotFoundException.class)
  public void shouldThrowNotFound() throws Exception {
    // when
    hotelManagementService.getHotel("simple");
  }

}
