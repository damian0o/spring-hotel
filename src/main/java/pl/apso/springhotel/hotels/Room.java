package pl.apso.springhotel.hotels;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(access = PRIVATE)
@Entity
public class Room {
  @Id
  @SequenceGenerator(name = "room_gen", sequenceName = "room_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_gen")
  private Long id;
  @NonNull
  private Integer price;
  @NonNull
  @ManyToOne(cascade = ALL)
  private Hotel hotel;
}
