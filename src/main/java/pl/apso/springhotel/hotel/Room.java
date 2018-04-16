package pl.apso.springhotel.hotel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PRIVATE;

/**
 * Represents room entity in database.
 */
@Data
@ToString(exclude = "hotel")
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(exclude = "hotel")
@Entity
@Builder
public class Room {
  @Id
  @SequenceGenerator(name = "room_gen", sequenceName = "room_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_gen")
  private Long id;
  private Integer price;
  @ManyToOne(cascade = ALL)
  @JsonBackReference
  private Hotel hotel;
  private boolean avail = true;

}
