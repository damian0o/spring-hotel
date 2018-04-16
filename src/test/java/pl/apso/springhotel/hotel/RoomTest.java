package pl.apso.springhotel.hotel;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomTest {

  @Test
  public void createRoom() {
    Hotel h = new Hotel("Temp Inn", "Krakow");
    Room r = Room.builder().price(100).hotel(h).build();
    assertThat(r.getPrice()).isEqualTo(100);
    assertThat(r.getHotel())
      .isEqualToIgnoringNullFields(
        new Hotel("Temp Inn", "Krakow")
      );
  }

}
