package pl.apso.springhotel.reservation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.apso.springhotel.hotels.Room;

import javax.persistence.*;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = PRIVATE)
@Entity
public class Reservation {
  @Id
  @GeneratedValue
  private Long id;
  @NonNull
  private LocalDate fromDate;
  @NonNull
  // to date is exclusive
  private LocalDate toDate;
  @NonNull
  @ManyToOne
  private Room room;
}
