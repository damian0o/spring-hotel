package pl.apso.springhotel.reservation;

import lombok.*;
import pl.apso.springhotel.hotel.Room;

import javax.persistence.*;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
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
