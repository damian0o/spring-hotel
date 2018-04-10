package pl.apso.springhotel.hotels;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.apso.springhotel.hotels.Hotel;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PRIVATE;

@Data
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
