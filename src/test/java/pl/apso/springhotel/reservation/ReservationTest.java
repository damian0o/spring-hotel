package pl.apso.springhotel.reservation;


import org.junit.Test;
import pl.apso.springhotel.hotel.Hotel;
import pl.apso.springhotel.hotel.Room;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTest {

  @Test
  public void createReservation() {
    Hotel hotel = new Hotel("Guava Inn", "Krakow");
    Room room = Room.builder().price(120).hotel(hotel).build();
    LocalDate from = LocalDate.now();
    LocalDate to = from.plus(1, DAYS);
    Reservation r = Reservation.builder().start(from).end(to).room(room).build();
    assertThat(r.getStart()).isEqualTo(from);
    assertThat(r.getEnd()).isEqualTo(to);
  }

}
