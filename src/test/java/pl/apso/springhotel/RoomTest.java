package pl.apso.springhotel;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomTest {

  @Test
  public void createRoom() {
    Hotel h = new Hotel("Temp Inn", "Krakow");
    Room r = new Room(100, h);
    assertThat(r.getPrice()).isEqualTo(100);
    assertThat(r.getHotel())
      .isEqualToIgnoringNullFields(
        new Hotel("Temp Inn", "Krakow")
      );
  }

}
