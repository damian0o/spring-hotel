package pl.apso.springhotel.hotels;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Hotel {
  @Id
  @GeneratedValue
  private Long id;
  @NonNull
  @Column(unique = true)
  private String name;
  @NonNull
  private String city;
}
