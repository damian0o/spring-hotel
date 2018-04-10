package pl.apso.springhotel;


import org.junit.Test;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTest {

  @Test
  public void createReservation() {
    Hotel hotel = new Hotel("Guava Inn", "Krakow");
    Room room = new Room(120, hotel);
    LocalDate from = LocalDate.now();
    LocalDate to = from.plus(1, DAYS);
    Reservation r = new Reservation(from, to, room);
    assertThat(r.getFromDate()).isEqualTo(from);
    assertThat(r.getToDate()).isEqualTo(to);
  }

}
