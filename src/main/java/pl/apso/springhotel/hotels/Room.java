package pl.apso.springhotel.hotels;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(access = PRIVATE)
@Entity
public class Room {
  @Id
  @GeneratedValue
  private Long id;
  @NonNull
  private Integer price;
  @NonNull
  @ManyToOne(cascade = ALL)
  private Hotel hotel;
}
