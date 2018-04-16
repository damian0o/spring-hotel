package pl.apso.springhotel.reservation;

import lombok.*;
import pl.apso.springhotel.hotel.Room;

import javax.persistence.*;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

/**
 * Represent reservation entity in database.
 */
@Data
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Entity
@Builder
public class Reservation {
  @Id
  @GeneratedValue
  private Long id;
  private LocalDate start;
  // to date is exclusive
  private LocalDate end;
  @ManyToOne
  private Room room;
  private boolean accepted;
}
