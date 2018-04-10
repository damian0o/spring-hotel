package pl.apso.springhotel.hotels;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor(access = PRIVATE)
@RequiredArgsConstructor
@Entity
public class Hotel {
  @Id
  @GeneratedValue
  private Long id;
  @NonNull
  private String name;
  @NonNull
  private String city;
}
