package pl.apso.springhotel.hotels;

import org.junit.Test;
import pl.apso.springhotel.hotels.Hotel;

import static org.assertj.core.api.Assertions.assertThat;

public class HotelTest {

  @Test
  public void hotel_CreateHotelInstance() {
    Hotel hotel = new Hotel("Spring Hotel", "Warsaw");
    assertThat(hotel.getName()).isEqualTo("Spring Hotel");
    assertThat(hotel.getCity()).isEqualTo("Warsaw");

    assertThat(hotel).isEqualTo(new Hotel("Spring Hotel", "Warsaw"));
    assertThat(hotel).isNotEqualTo(new Hotel("Lambda Inn", "Wroclaw"));
  }

}
