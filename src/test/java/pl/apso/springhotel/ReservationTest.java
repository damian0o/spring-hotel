package pl.apso.springhotel;


import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTest {

  @Test
  public void createReservation() {
    LocalDate from = LocalDate.now();
    LocalDate to = from.plus(1, ChronoUnit.DAYS);
    Reservation r = new Reservation(from, to);
    assertThat(r.getFromDate()).isEqualTo(from);
    assertThat(r.getToDate()).isEqualTo(to);
  }

}
