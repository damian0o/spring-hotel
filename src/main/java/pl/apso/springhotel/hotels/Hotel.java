package pl.apso.springhotel.hotels;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Hotel {
  @Id
  @SequenceGenerator(name = "hotel_gen", sequenceName = "hotel_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_gen")
  private Long id;
  @NonNull
  @Column(unique = true)
  private String name;
  @NonNull
  private String city;
}
